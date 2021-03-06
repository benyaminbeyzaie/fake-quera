package ir.sharif.fakequera.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.sharif.fakequera.dao.AnswerDao;
import ir.sharif.fakequera.dao.ClassDao;
import ir.sharif.fakequera.dao.QuestionDao;
import ir.sharif.fakequera.dao.UserDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.User;

public class AnswerRepository {
    private final AnswerDao answerDao;
    private final UserDao userDao;
    private final ClassDao classDao;
    private final QuestionDao questionDao;
    private final MutableLiveData<Answer> answerLiveData;
    private final MutableLiveData<String> message;
    private final MutableLiveData<List<Answer>> answerToQuestion;
    private int currentQuestionID;

    public AnswerRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        answerDao = db.answerDao();
        userDao = db.userDao();
        classDao = db.classDao();
        questionDao = db.questionDao();
        answerToQuestion = new MutableLiveData<>();

        answerLiveData = new MutableLiveData<>();
        message = new MutableLiveData<>();

    }


    public void answer(int questionId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            User currentUser = userDao.getCurrentUser();

            if (currentUser == null) {
                message.postValue("Authentication Failed!");
                return;
            }

            Answer answer = answerDao.getAnswersOfQuestion(questionId, currentUser.uid);


            if (answer == null) {
                message.postValue("Answer Not Found !");
                return;
            }

            answerLiveData.postValue(answer);
        });
    }

    public void addAnswer(int questionId, String answerContent) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            User currentUser = userDao.getCurrentUser();

            if (currentUser == null) {
                message.postValue("Authentication Failed!");
                return;
            }

            Question question = questionDao.get(questionId);


            if (question == null) {
                message.postValue("Question Not Found!");
                return;
            }

            if (!classDao.existStudentInClass(question.ownerClassId, currentUser.uid)) {
                message.postValue("You are not a member of this class!");
                return;
            }

            Answer answer = answerDao.getAnswersOfQuestion(questionId, currentUser.uid);

            if (answer == null) {
                answerDao.insert(new Answer(
                        currentUser.uid,
                        questionId,
                        answerContent
                ));
            } else {

                answer.content = answerContent;
                answerDao.update(answer);
            }


            message.postValue("Answer Submitted Successfully!");
        });
    }

    public void getAnswerOfQuestion(int questionID) {
        this.currentQuestionID = questionID;
        MutableLiveData<List<Answer>> result = new MutableLiveData<>();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Answer> answersOfQuestion = answerDao.getAnswersOfQuestion(questionID);
            answerToQuestion.postValue(answersOfQuestion);
        });
    }

    public MutableLiveData<List<Answer>> getAnswerToQuestion() {
        return answerToQuestion;
    }

    public MutableLiveData<Answer> getAnswerLiveData() {
        return answerLiveData;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void update(Answer answer) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            answerDao.update(answer);
            if (currentQuestionID != 0) {
                getAnswerOfQuestion(this.currentQuestionID);
            }

        });
    }
}
