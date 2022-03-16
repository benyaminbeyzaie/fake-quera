package ir.sharif.fakequera.repositories;

import android.app.Application;
import android.service.controls.actions.BooleanAction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.sharif.fakequera.dao.ClassDao;
import ir.sharif.fakequera.dao.QuestionDao;
import ir.sharif.fakequera.dao.StudentDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;
import ir.sharif.fakequera.dao.StudentDao;

public class ClassRepository {
    private final ClassDao classDao;
    private final QuestionDao questionDao;
    private final StudentDao studentDao;
    private final MutableLiveData<List<Class>> allClasses;
    private final MutableLiveData<List<Class>> studentClasses;
    private final MutableLiveData<String> message;
    private final MutableLiveData<Boolean> userIsInClass;


    public ClassRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        classDao = db.classDao();
        questionDao = db.questionDao();
        allClasses = new MutableLiveData<>();
        message = new MutableLiveData<>();
        studentDao = db.studentDao();
        studentClasses = new MutableLiveData<>();
        userIsInClass = new MutableLiveData<>();

        // fake data
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (classDao.all().size() == 0) {
                for (int i = 0; i < 10; i++) {
                    classDao.insert(new Class("Class " + i, i));
                }
                for (int i = 0; i < 10; i++) {
                    questionDao.insert(new Question(1, "Question  " + i));
                }
            }
        });
    }

    public void loadStudentClasses(int studentUId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Class> classesList = classDao.getClassesOfStudent(studentUId);
            studentClasses.postValue(classesList);
            message.postValue("classes are loaded");
        });
    }

    public void setClassToStudent(int studentUId, int classUId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Class aClass = classDao.getClassWithUId(classUId);
            if (aClass.students == null) {
                aClass.students = new ArrayList<>();
            }
            aClass.students.add(studentUId);
            classDao.update(aClass);
            message.postValue("class is set to student");
            loadStudentClasses(studentUId);
            checkUserId(classUId, studentUId);
        });
    }


    public void loadAllClasses() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Class> list = classDao.all();
            if (list == null || list.isEmpty()) {
                message.postValue("Class Not Found !");
                return;
            }
            allClasses.postValue(list);
        });
    }

    public LiveData<List<Class>> getAllClasses() {
        loadAllClasses();
        return allClasses;
    }

    public void checkUserId(int classId, int userId) {
        userIsInClass.postValue(false);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Class aClass = classDao.getClassWithUId(classId);
            if (aClass.students == null) {
                userIsInClass.postValue(false);
                return;
            }
            if (aClass.students.contains(userId)) {
                userIsInClass.postValue(true);
                return;
            }
            userIsInClass.postValue(false);
        });
    }

    public LiveData<List<Class>> getStudentClasses() {
        return studentClasses;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<Boolean> getUserIsInClass() {
        return userIsInClass;
    }

}
