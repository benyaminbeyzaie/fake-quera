package ir.sharif.fakequera.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Teacher extends User{
    @ColumnInfo(name = "university_name")
    public String universityName;

    public Teacher(@NonNull String userName, @NonNull String password, @NonNull String universityName) {
        super(userName, password, true);
        this.universityName = universityName;
    }

    @NonNull
    @Override
    public String toString() {
        return "Teacher{" +
                "universityName='" + universityName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isTeacher=" + isTeacher +
                '}';
    }
}