package ipc.true123.cn.ipc.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ipc.true123.cn.ipc.R;

/**
 * Created by junbo on 23/8/2016.
 */
public class MessengerActivity extends AppCompatActivity {
    public static final String TAG = "MessengerActivity";
    @BindView(R.id.sendmsg)
    Button sendMsg;

    @BindView(R.id.msg)
    EditText editText;
    Messenger messenger;
    Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "msg from remote service is " + msg.getData().getString("msg"));
            editText.setText(msg.getData().getString("msg"));
            super.handleMessage(msg);
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messenger_activity);
        ButterKnife.bind(this);
        bindService(new Intent(this, MessengerService.class), connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messenger = new Messenger(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messenger = null;
        }
    };

    @OnClick(R.id.sendmsg)
    public void setSendMsg() {
        if (messenger != null) {
            Message message = Message.obtain(null, 1);
            message.replyTo=clientMessenger;
            Bundle bundle = new Bundle();
            bundle.putString("msg", editText.getText().toString());
            message.setData(bundle);
            try {

                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
