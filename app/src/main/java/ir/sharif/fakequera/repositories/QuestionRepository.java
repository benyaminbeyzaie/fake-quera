package ir.sharif.fakequera.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.sharif.fakequera.dao.AnswerDao;
import ir.sharif.fakequera.dao.ClassDao;
import ir.sharif.fakequera.dao.QuestionDao;
import ir.sharif.fakequera.dao.TeacherDao;
import ir.sharif.fakequera.dao.UserDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;

public class QuestionRepository {
    private final QuestionDao questionDao;
    private final TeacherDao teacherDao;
    private final UserDao userDao;
    private final MutableLiveData<List<Question>> questionList;
    private final MutableLiveData<Teacher> teacherLiveData;
    private final MutableLiveData<Question> questionLiveData;
    private final MutableLiveData<String> message;

    public QuestionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        questionDao = db.questionDao();
        teacherDao = db.teacherDao();
        userDao = db.userDao();

        questionList = new MutableLiveData<>();
        teacherLiveData = new MutableLiveData<>();
        questionLiveData = new MutableLiveData<>();
        message = new MutableLiveData<>();

    }


    public void questionList(int classId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            List<Question> list = questionDao.getQuestionsOfClass(classId);
            if (list == null || list.isEmpty()) {
                message.postValue("Question Not Found In This Class !");
                return;
            }

            questionList.postValue(list);
        });
    }

    public void teacher(int teacherId){
        AppDatabase.databaseWriteExecutor.execute(() -> {

            Teacher teacher = teacherDao.getTeacher(teacherId);

            teacherLiveData.postValue(teacher);
        });
    }

    public void question(int questionId){
        AppDatabase.databaseWriteExecutor.execute(() -> {

            Question question = questionDao.get(questionId);

            questionLiveData.postValue(question);
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

    public MutableLiveData<String> getMessage() {
        return message;
    }
}
