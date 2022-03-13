package ir.sharif.fakequera.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Teacher extends User{
    @ColumnInfo(name = "university_name")
    public String universityName;

    public Teacher(@NonNull String userName, @NonNull String password) {
        super(userName, password, true);
    }
}