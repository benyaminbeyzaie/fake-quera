package ir.sharif.fakequera.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import ir.sharif.fakequera.entities.Student;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student WHERE uid LIKE :id")
    Student getStudent(int id);

    @Query("SELECT * FROM student WHERE user_name LIKE :username")
    Student getStudent2(String username);

    @Insert
    void insert(Student student);

    @Update
    void update(Student student);
}
