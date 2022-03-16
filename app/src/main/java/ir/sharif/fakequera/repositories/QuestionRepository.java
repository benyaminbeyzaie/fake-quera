package ir.sharif.fakequera.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.sharif.fakequera.dao.AnswerDao;
import ir.sharif.fakequera.dao.QuestionDao;
import ir.sharif.fakequera.dao.TeacherDao;
import ir.sharif.fakequera.dao.UserDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;

public class QuestionRepository {
    private QuestionDao questionDao;
    private TeacherDao teacherDao;
    private AnswerDao answerDao;
    private UserDao userDao;
    private MutableLiveData<List<Question>> questionList;
    private MutableLiveData<Teacher> teacherLiveData;
    private MutableLiveData<Question> questionLiveData;
    private MutableLiveData<Answer> answerLiveData;
    private MutableLiveData<String> message;

    private LiveData<List<Question>> questions;
    private int classUID;

    public QuestionRepository(Application application, int uid) {
        AppDatabase db = AppDatabase.getDatabase(application);
        questionDao = db.questionDao();
        this.classUID = uid;
        questions = questionDao.getQuestionsOfClass(this.classUID);

    }

    public QuestionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        questionDao = db.questionDao();
        teacherDao = db.teacherDao();
        answerDao = db.answerDao();
        userDao = db.userDao();

        questionList = new MutableLiveData<>();
        teacherLiveData = new MutableLiveData<>();
        questionLiveData = new MutableLiveData<>();
        answerLiveData = new MutableLiveData<>();
        message = new MutableLiveData<>();
    }


    public void questionList(int classId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            List<Question> list = questionDao.getQuestionsOfClass2(classId);
            if (list == null || list.isEmpty()) {
                message.postValue("Question Not Found In This Class !");
                return;
            }

            questionList.postValue(list);
        });
    }

    public void teacher(int teacherId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            Teacher teacher = teacherDao.getTeacher(teacherId);

            teacherLiveData.postValue(teacher);
        });
    }

    public void question(int questionId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            Question question = questionDao.get(questionId);

            questionLiveData.postValue(question);
        });
    }

    public void answer(int questionId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            User currentUser = userDao.getCurrentUser();

            if (currentUser == null) {
                message.postValue("Authentication Failed !");
                return;
            }

            Answer answer = answerDao.getAnswersOfQuestion(questionId, currentUser.uid);


            if (answer == null) {
//                message.postValue("Answer Not Found !");
                return;
            }

            answerLiveData.postValue(answer);
        });
    }

    public void addAnswer(int questionId, String answerContent) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            User currentUser = userDao.getCurrentUser();

            if (currentUser == null) {
                message.postValue("Authentication Failed !");
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


            message.postValue("Submit Answer Success !");
        });
    }

    public MutableLiveData<List<Question>> getQuestionList() {
        return questionList;
    }

    public MutableLiveData<Teacher> getTeacherLiveData() {
        return teacherLiveData;
    }

    public MutableLiveData<Question> getQuestionLiveData() {
        return questionLiveData;
    }

    public MutableLiveData<Answer> getAnswerLiveData() {
        return answerLiveData;
    }

    public MutableLiveData<String> getMessage() {
        return message;
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

    public LiveData<List<Question>> getQuestions() {
        return questions;
    }

    public void update(int classUID) {
        this.classUID = classUID;
        questions = questionDao.getQuestionsOfClass(this.classUID);
    }

    public LiveData<Question> getQuestion(int uid){
        return questionDao.get2(uid);
    }
}
