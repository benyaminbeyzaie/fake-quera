package ir.sharif.fakequera.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student WHERE uid LIKE :id")
    Student getStudent(int id);

    @Insert
    void insert(Student student);
}
