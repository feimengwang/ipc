package ipc.true123.cn.ipc.aidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ipc.true123.cn.ipc.OnNewStudentInListener;
import ipc.true123.cn.ipc.R;
import ipc.true123.cn.ipc.Student;
import ipc.true123.cn.ipc.StudentsManager;

/**
 * Created by junbo on 23/8/2016.
 */
public class AIDLActivity extends AppCompatActivity{
    @BindView(R.id.addAndGetList)
    Button addAndGet;
    @BindView(R.id.textview)
    TextView textView;
    @BindView(R.id.register)
    Button rigister;
    @BindView(R.id.unregister)
    Button unrigister;
    @BindView(R.id.onreturn)
    TextView onreturn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl_activity);
        ButterKnife.bind(this);
        bindService(new Intent(this,AIDLService.class),connection, Context.BIND_AUTO_CREATE);
    }
    StudentsManager service;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = StudentsManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @OnClick(R.id.addAndGetList)
    public void setAddAndGet(){
        if(service!=null && service.asBinder().isBinderAlive()){
            try {
                service.addStudent(new Student((int) (Math.random()*50),"nihao"));
                List<Student> list = service.getStudents();
                textView.setText(list.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }
    OnNewStudentInListener listener=new OnNewStudentInListener.Stub() {
        @Override
        public void onNewStudentIn(Student student) throws RemoteException {
            handler.sendMessage(Message.obtain(null,1,student));
        }


    };
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            onReceive((Student) msg.obj);
            super.handleMessage(msg);
        }
    };
    private void onReceive(Student student){
        onreturn.setText(student.toString());
    }
    @OnClick(R.id.register)
    public void register(){
        if(service!=null){
            try {
                service.register(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.unregister)
    public void unregister(){
        if(service!=null){
            try {
                service.unRegister(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            service.unRegister(listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(connection);

    }
}
