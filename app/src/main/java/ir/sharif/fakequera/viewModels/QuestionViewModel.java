package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.repositories.QuestionRepository;

public class QuestionViewModel extends AndroidViewModel {

    public final QuestionRepository repository;
    private  LiveData<List<Question>> questionList;
    private  LiveData<Teacher> teacherLiveData;
    private  LiveData<Question> questionLiveData;
    private  LiveData<Answer> answerLiveData;
    private  LiveData<String> message;

    public QuestionViewModel(Application application, int uid) {
        super(application);
        repository = new QuestionRepository(application, uid);
        questionList = repository.getQuestions();
    }

    public QuestionViewModel(Application application) {
        super(application);
        repository = new QuestionRepository(application);
        questionList = repository.getQuestionList();
        teacherLiveData = repository.getTeacherLiveData();
        questionLiveData = repository.getQuestionLiveData();
        answerLiveData = repository.getAnswerLiveData();
        message = repository.getMessage();
    }

    public LiveData<List<Question>> getQuestionList() {
        return questionList;
    }

    public LiveData<Teacher> getTeacherLiveData() {
        return teacherLiveData;
    }

    public LiveData<Answer> getAnswerLiveData() {
        return answerLiveData;
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

    public void answer(int questionId){
        repository.answer(questionId);
    }

    public void addAnswer(int questionId, String answer) {
        repository.addAnswer(questionId , answer);
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

    public LiveData<List<Question>> getClassQuestions() {
        return questionList;
    }

    public void update(int uid) {
        repository.update(uid);
        questionList = repository.getQuestions();
    }

    public LiveData<Question> getQuestion(int uid){
        return repository.getQuestion(uid);
    }

}

