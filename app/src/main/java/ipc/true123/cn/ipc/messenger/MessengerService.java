package ipc.true123.cn.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by junbo on 23/8/2016.
 */
public class MessengerService extends Service {
    public static final String TAG="MessengerService";
    Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.i(TAG,""+msg.getData().getString("msg"));
                    if(msg.replyTo!=null){
                        Messenger client = msg.replyTo;
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("msg","this is from remote service");
                        message.setData(bundle);
                        try {
                            client.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

            }
            super.handleMessage(msg);
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
