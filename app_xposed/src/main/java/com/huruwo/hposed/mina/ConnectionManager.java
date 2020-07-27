package com.huruwo.hposed.mina;


import android.content.Context;
import android.util.Log;


import com.huruwo.hposed.kshook.HookMethod;
import com.huruwo.hposed.utils.GsonUtils;
import com.huruwo.hposed.utils.LogXUtils;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.huruwo.hposed.bean.*;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.util.Map;

import de.robv.android.xposed.XposedHelpers;

import static com.huruwo.hposed.utils.GsonUtils.fromJson;

/**
 * Description:
 * User: chenzheng
 * Date: 2016/12/9 0009
 * Time: 16:21
 */
public class ConnectionManager {

    private ConnectionConfig mConfig;
    private WeakReference<Context> mContext;

    private NioSocketConnector mConnection;
    private IoSession mSession;
    private InetSocketAddress mAddress;

    private Map<String, Class<?>> signMap;


    public ConnectionManager(ConnectionConfig config, Map<String, Class<?>> signMap) {
        this.mConfig = config;
        this.mContext = new WeakReference<Context>(config.getContext());
        this.signMap = signMap;
        init(signMap);
    }

    private void init(Map<String, Class<?>> signMap) {
        mAddress = new InetSocketAddress(mConfig.getIp(), mConfig.getPort());
        mConnection = new NioSocketConnector();
        mConnection.getSessionConfig().setReadBufferSize(mConfig.getReadBufferSize());
        mConnection.getFilterChain().addLast("logging", new LoggingFilter());
        mConnection.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        mConnection.getFilterChain().addFirst("reconnection", new IoFilterAdapter() {
            @Override
            public void sessionClosed(NextFilter nextFilter, IoSession ioSession) throws Exception {
                for (; ; ) {
                    try {
                        Thread.sleep(3000);
                        ConnectFuture future = mConnection.connect();
                        future.awaitUninterruptibly();// 等待连接创建成功
                        mSession = future.getSession();// 获取会话
                        if (mSession.isConnected()) {
                            LogXUtils.e("断线重连[" + mConnection.getDefaultRemoteAddress().getHostName() + ":" + mConnection.getDefaultRemoteAddress().getPort() + "]成功");
                            break;
                        }
                    } catch (Exception ex) {
                        LogXUtils.e("重连服务器登录失败,3秒再连接一次:" + ex.getMessage());
                    }
                }
            }
        });
        mConnection.setHandler(new DefaultHandler(mContext.get(), signMap));
        mConnection.setDefaultRemoteAddress(mAddress);
    }

    /**
     * 与服务器连接
     *
     * @return
     */
    public boolean connnect() {
        Log.e("tag", "准备连接");
        try {
            ConnectFuture future = mConnection.connect();
            future.awaitUninterruptibly();
            mSession = future.getSession();

            SessionManager.getInstance().setSeesion(mSession);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tag", "连接失败");
            return false;
        }

        return mSession == null ? false : true;
    }

    /**
     * 断开连接
     */
    public void disContect() {
        mConnection.dispose();
        mConnection = null;
        mSession = null;
        mAddress = null;
        mContext = null;
        Log.e("douyin", "断开连接");
    }

    private static class DefaultHandler extends IoHandlerAdapter {

        private Context mContext;

        private Map<String, Class<?>> signMap;

        private DefaultHandler(Context context, Map<String, Class<?>> signMap) {
            this.mContext = context;
            this.signMap = signMap;
        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            super.sessionOpened(session);
        }

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {

            String msg = message.toString();
            LogXUtils.e("接收到服务器端消息：" + msg);
            String res = "";
            try {
                MessageBean messageBean = fromJson(msg, MessageBean.class);
                String messData = messageBean.getData();
                if (messageBean.getAction().equals("call_sig3")) {
                   res= HookMethod.getSign3(signMap,messData);
                } else if (messageBean.getAction().equals("call_egid")) {
                    res =   HookMethod.getDeviceInfo(signMap,messData);
                }


            } catch (Exception e) {
                res = e.getMessage();
            }

            LogXUtils.e("发送给服务器端消息：" + res);
            session.write(res);

        }
    }
}

