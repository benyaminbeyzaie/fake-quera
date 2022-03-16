package ir.sharif.fakequera.repositories;

import android.app.Application;

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
    private final MutableLiveData<List<Class>> allClasses;
    private final MutableLiveData<String> message;
    private final StudentDao studentDao;
    private final MutableLiveData<List<Class>> studentClasses;

    public ClassRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        classDao = db.classDao();
        questionDao = db.questionDao();
        allClasses = new MutableLiveData<>();
        message = new MutableLiveData<>();
        studentDao = db.studentDao();
        studentClasses = new MutableLiveData<>();
        // fake data
//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            if (classDao.all().size() == 0) {
//                for (int i = 0; i < 10; i++) {
//
//                    classDao.insert(new Class("Class " + i));
//                }
//                for (int i = 0; i < 10; i++) {
//
//                    questionDao.insert(new Question(1, "Question  " + i));
//                }
//            }
//        });
    }

    public LiveData<List<Class>> getClassesOfStudent(int studentUId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Class> classesList = classDao.getClassesOfStudent(studentUId);
            studentClasses.postValue(classesList);
            message.postValue("classes are loaded");
        });
        return studentClasses;
    }

    public void setClassToStudent(int studentUId, int classUId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Class aClass = classDao.getClassWithUId(classUId);
            aClass.students.add(studentUId);
            classDao.update(aClass);
            message.postValue("class is set to student");
            getClassesOfStudent(studentUId);
        });
    }


    public void getAllClasses() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Class> list = classDao.all();
            if (list == null || list.isEmpty()) {
                message.postValue("Class Not Found !");
                return;
            }

            allClasses.postValue(list);
        });
    }

    public MutableLiveData<List<Class>> getClassList() {
        return allClasses;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }
}
