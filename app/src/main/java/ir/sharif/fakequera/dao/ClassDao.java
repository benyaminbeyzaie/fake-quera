package ir.sharif.fakequera.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.sharif.fakequera.entities.Class;

@Dao
public interface ClassDao {
    @Query("SELECT * FROM Class WHERE owner_teacher_id LIKE :teacherId")
    List<Class> getClassesOfTeacher(int teacherId);

    @Query("SELECT * FROM Class WHERE students LIKE '%' || :studentId || '%'")
    List<Class> getClassesOfStudent(int studentId);

    @Query("SELECT * FROM Class WHERE uid LIKE :uId")
    Class getClassWithUId(int uId);

    @Insert
    void insert(Class c);

    @Delete
    void delete(Class c);

    @Update
    void update(Class c);
}
