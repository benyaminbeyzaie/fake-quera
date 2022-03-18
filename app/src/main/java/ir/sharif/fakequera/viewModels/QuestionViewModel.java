package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.repositories.QuestionRepository;

public class QuestionViewModel extends AndroidViewModel {

    public final QuestionRepository repository;
    private LiveData<List<Question>> questionList;
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

    public MutableLiveData<Question> question(int questionId){
        return repository.question(questionId);
    }

    public void getClassQuestions(int classID){
        repository.questionList(classID);
        questionList = repository.getQuestionList();
    }

    public void insert(Question question) {
        repository.insert(question);
    }

    public void update(Question question) {
        repository.update(question);
    }


    public void delete(Question question) {
        repository.delete(question);
    }

}

