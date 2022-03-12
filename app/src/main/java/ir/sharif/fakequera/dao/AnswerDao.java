package ir.sharif.fakequera.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.sharif.fakequera.entities.Answer;

@Dao
public interface AnswerDao {
    @Query("SELECT * FROM Answer WHERE uid LIKE :questionId")
    List<Answer> getAnswersOfQuestion(int questionId);

    @Insert
    void insert(Answer answer);

    @Delete
    void delete(Answer answer);

    @Update
    void update(Answer answer);
}
