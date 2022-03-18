package ir.sharif.fakequera.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.sharif.fakequera.entities.Question;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM question WHERE ownerClassId LIKE :classId")
    LiveData<List<Question>> getQuestionsOfClass(int classId);

    @Query("SELECT * FROM question WHERE ownerClassId = :classId")
    List<Question> getQuestionsOfClass2(int classId);

    @Query("SELECT * FROM question WHERE uid = :questionId")
    Question get(int questionId);

    @Query("SELECT * FROM question WHERE uid = :questionId")
    LiveData<Question> get2(int questionId);

    @Insert
    void insert(Question question);

    @Delete
    void delete(Question question);

    @Update
    void update(Question question);
}
