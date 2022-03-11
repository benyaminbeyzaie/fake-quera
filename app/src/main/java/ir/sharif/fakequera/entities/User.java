package ir.sharif.fakequera.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

// TODO: make this class abstract and add two type of users!
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

//    @ColumnInfo(name = "first_name")
//    public String firstName;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "mode")
    public String mode;

    @ColumnInfo(name = "is_current_user")
    public boolean isCurrentUser = false;


    public User(String userName, String password, String mode) {
        this.userName = userName;
        this.password = password;
        this.mode = mode;
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCurrentUser() {
        return isCurrentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        isCurrentUser = currentUser;
    }
}
