package ipc.true123.cn.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ipc.true123.cn.ipc.aidl.AIDLActivity;
import ipc.true123.cn.ipc.messenger.MessengerActivity;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.messenger)
    Button messenger;
    @BindView(R.id.aidl)
    Button aidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.messenger)
    public void startMessenger() {
        startActivity(new Intent(this, MessengerActivity.class));
    }

    @OnClick(R.id.aidl)
    public void startAIDL() {
        startActivity(new Intent(this, AIDLActivity.class));
    }
}
