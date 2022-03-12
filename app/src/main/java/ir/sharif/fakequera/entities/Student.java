package ir.sharif.fakequera.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Student extends User {
    @ColumnInfo(name = "student_id")
    public String studentId;

    public Student(@NonNull String userName, @NonNull String password) {
        super(userName, password, false);
    }
}
