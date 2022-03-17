package ir.sharif.fakequera.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.sharif.fakequera.entities.Answer;

@Dao
public interface AnswerDao {
    @Query("SELECT * FROM answer WHERE question_id LIKE :questionId")
    List<Answer> getAnswersOfQuestion(int questionId);

    @Query("SELECT * FROM Answer WHERE question_id = :questionId AND owner_id = :userId ")
    Answer getAnswersOfQuestion(int questionId , int userId);

    @Insert
    void insert(Answer answer);

    @Delete
    void delete(Answer answer);

    @Update
    void update(Answer answer);
}
