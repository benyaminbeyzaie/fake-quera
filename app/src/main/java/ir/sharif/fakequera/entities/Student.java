package ir.sharif.fakequera.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity
public class Student extends User {
    @ColumnInfo(name = "student_id")
    public String studentId;

    @Ignore
    public Student(@NonNull String userName, @NonNull String password, String name, String studentId) {
        super(userName, password, false, name);
        this.studentId = studentId;
    }

    public Student() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
