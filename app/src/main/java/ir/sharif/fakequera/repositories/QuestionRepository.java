package ir.sharif.fakequera.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ir.sharif.fakequera.dao.QuestionDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;

public class QuestionRepository {

    private QuestionDao questionDao;
    private LiveData<List<Question>> questions;
    private int classUID;


    public QuestionRepository(Application application , int uid){
        AppDatabase db = AppDatabase.getDatabase(application);
        questionDao = db.questionDao();
        this.classUID = uid;
        questions = questionDao.getQuestionsOfClass(this.classUID);
    }

    public void insert(Question question) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            questionDao.insert(question);
        });
    }

    public void update(Question question) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            questionDao.update(question);
        });
    }

    public void delete(Question question) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            questionDao.update(question);
        });
    }

    public LiveData<List<Question>> getQuestions(){
        return questions;
    }

    public void update(int classUID){
        this.classUID = classUID;
        questions = questionDao.getQuestionsOfClass(this.classUID);
    }
}
