package ir.sharif.fakequera.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Student extends User {
    @ColumnInfo(name = "student_id")
    public String studentId;

    public Student(@NonNull String userName, @NonNull String password, @NonNull String studentId) {
        super(userName, password, false);
        this.studentId = studentId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isTeacher=" + isTeacher +
                '}';
    }
}
