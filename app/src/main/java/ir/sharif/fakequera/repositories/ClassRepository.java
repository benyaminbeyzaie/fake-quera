package ir.sharif.fakequera.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.sharif.fakequera.dao.ClassDao;
import ir.sharif.fakequera.dao.QuestionDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;

public class ClassRepository {
    private final ClassDao classDao;
    private final QuestionDao questionDao;
    private final MutableLiveData<List<Class>> classList;
    private final MutableLiveData<String> message;


    private UserRepository userRepository;
    private LiveData<List<Class>> classes;
    private int uid;


    public ClassRepository(Application application , int uid) {
        AppDatabase db = AppDatabase.getDatabase(application);
        classDao = db.classDao();
        questionDao = db.questionDao();
        userRepository = UserRepository.getInstance(application);
        classList = new MutableLiveData<>();
        message = new MutableLiveData<>();
        this.uid = uid;
        classes = classDao.getClassesOfTeacher(this.uid);
    }

    public ClassRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        classDao = db.classDao();
        questionDao = db.questionDao();
        userRepository = UserRepository.getInstance(application);
        classList = new MutableLiveData<>();
        message = new MutableLiveData<>();
    }


    public void classList() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Class> list = classDao.all();
            if (list == null || list.isEmpty()) {
                message.postValue("Class Not Found !");
                return;
            }

            classList.postValue(list);
        });
    }

    public MutableLiveData<List<Class>> getClassList() {
        return classList;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void insert(Class clas){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            classDao.insert(clas);
        });
    }

    public void update(Class clas) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            classDao.update(clas);
        });
    }

    public void delete(Class clas) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            classDao.delete(clas);
        });
    }

    public LiveData<List<Class>> getClasses() {
        return classes;
    }

    public void update(int uid) {
        Log.d("mym", "update uid" + uid);
        this.uid = uid;
        classes = classDao.getClassesOfTeacher(this.uid);
    }

}
