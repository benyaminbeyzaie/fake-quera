package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.repositories.ClassRepository;
import ir.sharif.fakequera.repositories.QuestionRepository;

public class QuestionViewModel extends AndroidViewModel {

    public final QuestionRepository repository;
    private final LiveData<List<Question>> questionList;
    private final LiveData<Teacher> teacherLiveData;
    private final LiveData<Question> questionLiveData;
    private final LiveData<String> message;

    public QuestionViewModel(Application application) {
        super(application);
        repository = new QuestionRepository(application);
        questionList = repository.getQuestionList();
        teacherLiveData = repository.getTeacherLiveData();
        questionLiveData = repository.getQuestionLiveData();
        message = repository.getMessage();
    }

    public LiveData<List<Question>> getQuestionList() {
        return questionList;
    }

    public LiveData<Teacher> getTeacherLiveData() {
        return teacherLiveData;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public void questionList(int classId){
        repository.questionList(classId);
    }

    public LiveData<Question> getQuestionLiveData() {
        return questionLiveData;
    }

    public void teacher(int teacherId){
        repository.teacher(teacherId);
    }

    public void question(int questionId){
        repository.question(questionId);
    }

}

