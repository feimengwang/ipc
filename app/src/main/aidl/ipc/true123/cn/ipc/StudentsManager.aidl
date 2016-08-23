// StudentsManager.aidl
package ipc.true123.cn.ipc;

// Declare any non-default types here with import statements
import ipc.true123.cn.ipc.Student;
import ipc.true123.cn.ipc.OnNewStudentInListener;
interface StudentsManager {
    List getStudents();
    void addStudent(in Student student);
    void register(in OnNewStudentInListener listener);
    void unRegister(in OnNewStudentInListener listner);
}
