package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.repositories.QuestionRepository;

public class QuestionViewModel extends AndroidViewModel {

    private final QuestionRepository questionRepository;
    private LiveData<List<Question>> classQuestions;
    public QuestionViewModel(@NonNull Application application , int uid) {
        super(application);
        questionRepository = new QuestionRepository(application , uid);
        classQuestions = questionRepository.getQuestions();

    }

    public void insert(Question question) {
        questionRepository.insert(question);
    }

    public void update(Question question) {
        questionRepository.update(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public LiveData<List<Question>> getClassQuestions() {
        return classQuestions;
    }

    public void update(int uid) {
        questionRepository.update(uid);
        classQuestions = questionRepository.getQuestions();
    }
}
