package ipc.true123.cn.ipc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ipc.true123.cn.ipc.messenger.MessengerActivity;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.messenger)
    Button messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.messenger)
    public void startMessenger(){
        startActivity(new Intent(this, MessengerActivity.class));
    }
}
