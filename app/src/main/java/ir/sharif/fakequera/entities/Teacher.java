package ir.sharif.fakequera.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity
public class Teacher extends User{
    @ColumnInfo(name = "university_name")
    public String universityName;

    @Ignore
    public Teacher(@NonNull String userName, @NonNull String password , String name , String universityName) {
        super(userName, password, true , name );
        this.universityName = universityName;
    }

    public Teacher() {
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}