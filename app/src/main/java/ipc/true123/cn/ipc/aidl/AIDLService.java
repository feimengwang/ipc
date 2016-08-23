package ipc.true123.cn.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ipc.true123.cn.ipc.Student;
import ipc.true123.cn.ipc.StudentsManager;

/**
 * Created by junbo on 23/8/2016.
 */
public class AIDLService extends Service {
    CopyOnWriteArrayList<Student> students = new CopyOnWriteArrayList<Student>();
    Binder binder = new StudentsManager.Stub() {
        @Override
        public List getStudents() throws RemoteException {
            return students;
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            students.add(student);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
