package ir.sharif.fakequera.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.sharif.fakequera.entities.Question;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM question WHERE ownerClassId = :classId")
    List<Question> getQuestionsOfClass(int classId);

    @Query("SELECT * FROM question WHERE uid = :questionId")
    Question get(int questionId);

    @Insert
    void insert(Question question);

    @Delete
    void delete(Question question);

    @Update
    void update(Question question);
}
