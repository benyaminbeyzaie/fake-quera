package ir.sharif.fakequera.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "is_current_user")
    public boolean isCurrentUser = false;

    @ColumnInfo(name = "in_teacher")
    public boolean isTeacher;

    public User(@NotNull String userName, @NotNull String password, boolean isTeacher){
        this.userName = userName;
        this.password = password;
        this.isTeacher = isTeacher;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
