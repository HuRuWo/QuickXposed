package com.huruwo.hposed.mina;

import android.content.Context;
import android.os.HandlerThread;
import android.util.Log;

import com.huruwo.hposed.utils.LogXUtils;

import java.util.Map;

/**
 * Description:
 * User: chenzheng
 * Date: 2016/12/9 0009
 * Time: 17:17
 */
public class MinaHookService{


    private ConnectionThread thread;

    public void onCreate(Context context , String host , Integer port , ClassLoader cl , Map<String, Class<?>> signMap) {
        thread = new ConnectionThread("mina", context, host, port , cl , signMap);
        thread.start();
        LogXUtils.e( "启动线程尝试连接");
    }

    //这个thread 要启动在 attach 的服务中
    class ConnectionThread extends HandlerThread {

        private Context context;
        boolean isConnection;
        ConnectionManager mManager;
        public ConnectionThread(String name, Context context, String host , Integer port , ClassLoader cl , Map<String, Class<?>> signMap){
            super(name);
            this.context = context;

            ConnectionConfig config = new ConnectionConfig.Builder(context)
                    .setIp(host)
                    .setPort(Integer.valueOf(port))
                    .setReadBufferSize(10240)
                    .setConnectionTimeout(10000).builder();

            Thread.currentThread().setContextClassLoader(cl);
            mManager = new ConnectionManager(config , signMap);
        }

        @Override
        protected void onLooperPrepared() {
            while(true){
                isConnection = mManager.connnect();
                if(isConnection){
                    LogXUtils.e( "连接成功");
                    break;
                }
                try {
                    LogXUtils.e( "尝试重新连接");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void disConnect(){
            mManager.disContect();
        }
    }
}
