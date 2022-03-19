package ir.sharif.fakequera.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ir.sharif.fakequera.entities.Teacher;

@Dao
public interface TeacherDao {
    @Query("SELECT * FROM teacher WHERE uid LIKE :id")
    Teacher getTeacher(int id);

    @Query("SELECT * FROM teacher WHERE user_name LIKE :username")
    Teacher getTeacher2(String username);

    @Insert
    void insert(Teacher teacher);
}
