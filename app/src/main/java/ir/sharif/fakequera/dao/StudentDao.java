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

    @Insert
    void insert(Student student);

    @Update
    void update(Student student);
}
