// StudentsManager.aidl
package ipc.true123.cn.ipc;

// Declare any non-default types here with import statements
import ipc.true123.cn.ipc.Student;
interface StudentsManager {
    List getStudents();
    void addStudent(in Student student);
}
