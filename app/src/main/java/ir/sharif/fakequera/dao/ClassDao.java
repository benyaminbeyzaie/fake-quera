package ir.sharif.fakequera.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.sharif.fakequera.entities.Class;

@Dao
public interface ClassDao {
    @Query("SELECT * FROM class_table WHERE owner_teacher_id LIKE :teacherId")
    LiveData<List<Class>> getClassesOfTeacher(int teacherId);


    @Query("SELECT * FROM class_table")
    List<Class> all();

    @Query("SELECT * FROM class_table WHERE students LIKE '%' || :studentId || '%'")
    List<Class> getClassesOfStudent(int studentId);

    @Query("SELECT * FROM class_table WHERE uid LIKE :uId")
    Class getClassWithUId(int uId);

    @Query("SELECT EXISTS(SELECT * FROM class_table WHERE uid = :classId AND students LIKE '%' || :studentId || '%')")
    boolean existStudentInClass(int classId , int studentId);

    @Insert
    void insert(Class c);

    @Delete
    void delete(Class c);

    @Update
    void update(Class c);

}
