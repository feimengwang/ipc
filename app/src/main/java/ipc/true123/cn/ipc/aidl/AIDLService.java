package ipc.true123.cn.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ipc.true123.cn.ipc.OnNewStudentInListener;
import ipc.true123.cn.ipc.Student;
import ipc.true123.cn.ipc.StudentsManager;

/**
 * Created by junbo on 23/8/2016.
 */
public class AIDLService extends Service {
    private static final String TAG="AIDLService";
    CopyOnWriteArrayList<Student> students = new CopyOnWriteArrayList<Student>();
    RemoteCallbackList<OnNewStudentInListener> listeners = new RemoteCallbackList<OnNewStudentInListener>();
    Binder binder = new StudentsManager.Stub() {
        @Override
        public List getStudents() throws RemoteException {
            return students;
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            students.add(student);
            notice(student);
        }

        @Override
        public void register(OnNewStudentInListener listener) throws RemoteException {
           listeners.register(listener);
        }

        @Override
        public void unRegister(OnNewStudentInListener lisentener) throws RemoteException {
           listeners.unregister(lisentener);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void notice(Student student){
      int count=  listeners.beginBroadcast();
        Log.i(TAG,"count"+count);
        for(int i=0;i<count;i++){
           OnNewStudentInListener listener= listeners.getBroadcastItem(i);
            try {
                listener.onNewStudentIn(student);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        listeners.finishBroadcast();
    }
}
