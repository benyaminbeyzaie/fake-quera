package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.repositories.AnswerRepository;
import ir.sharif.fakequera.repositories.QuestionRepository;

public class AnswerViewModel extends AndroidViewModel {

    public final AnswerRepository repository;
    private final LiveData<Answer> answerLiveData;
    private final LiveData<String> message;

    public AnswerViewModel(Application application) {
        super(application);
        repository = new AnswerRepository(application);
        answerLiveData = repository.getAnswerLiveData();
        message = repository.getMessage();
    }

    public LiveData<Answer> getAnswerLiveData() {
        return answerLiveData;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public void answer(int questionId){
        repository.answer(questionId);
    }

    public void addAnswer(int questionId, String answer) {
        repository.addAnswer(questionId , answer);
    }

    public void getAnswerOfQuestuion(int questionID){
        repository.getAnswerOfQuestuion(questionID);
    }

    public LiveData<List<Answer>> getAnswerOfQuestuion(){
        return repository.getAnswerToQuestion();
    }

    public void giveScoreToAnswer(Answer answer){
        repository.update(answer);
    }
}


