package ir.sharif.fakequera.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE user_name LIKE :username")
    User findByUsername(String username);

    @Query("SELECT * FROM user WHERE is_current_user")
    User getCurrentUser();

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("UPDATE user SET is_current_user = 0")
    void deactivateAllUsers();
}
