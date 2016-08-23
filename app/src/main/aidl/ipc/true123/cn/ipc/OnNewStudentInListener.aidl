// OnNewStudentIn.aidl
package ipc.true123.cn.ipc;

// Declare any non-default types here with import statements
import ipc.true123.cn.ipc.Student;
interface OnNewStudentInListener {
    void onNewStudentIn(in Student student);
}
