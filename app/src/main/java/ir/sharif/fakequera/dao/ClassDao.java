package ir.sharif.fakequera.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.User;

@Dao
public interface ClassDao {
    @Query("SELECT * FROM class WHERE uid LIKE :teacherId")
    List<Class> getClassesOfTeacher(int teacherId);

    @Insert
    void insert(Class c);

    @Delete
    void delete(Class c);

    @Update
    void update(Class c);
}