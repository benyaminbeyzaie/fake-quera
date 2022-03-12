package ir.sharif.fakequera.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;

@Dao
public interface TeacherDao {
    @Query("SELECT * FROM teacher WHERE uid LIKE :id")
    Teacher getTeacher(int id);

    @Insert
    void insert(Teacher teacher);
}
