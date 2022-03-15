package ir.sharif.fakequera.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Objects;

import ir.sharif.fakequera.dao.ClassDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;

public class ClassRepository {

    private ClassDao classDao;
    private UserRepository userRepository;
    private LiveData<List<Class>> classes;
    private int uid;

    public ClassRepository(Application application, int uid) {
        Log.d("mym", "teacher uid is " + uid);
        AppDatabase db = AppDatabase.getDatabase(application);
        userRepository = UserRepository.getInstance(application);
        classDao = db.classDao();
        this.uid = uid;
        classes = classDao.getClassesOfTeacher(this.uid);
    }

    public void insert(Class clas) {
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
