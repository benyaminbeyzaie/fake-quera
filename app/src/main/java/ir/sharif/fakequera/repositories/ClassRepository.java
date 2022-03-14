package ir.sharif.fakequera.repositories;

import android.app.Application;
import android.os.AsyncTask;

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
    private MutableLiveData<List<Class>> classes;
    private MutableLiveData<Teacher> teacher;

    public ClassRepository(Application application , int uid){
        AppDatabase db = AppDatabase.getDatabase(application);
        userRepository = UserRepository.getInstance(application);
        classDao = db.classDao();
        teacher = (MutableLiveData<Teacher>) userRepository.getCurrentTeacher();
        classes = (MutableLiveData<List<Class>>) classDao.getClassesOfTeacher(uid);


    }

    public void insert(Class clas){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            classDao.insert(clas);
        });
    }

    public void update(Class clas){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            classDao.update(clas);
        });
    }

    public void delete(Class clas){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            classDao.delete(clas);
        });
    }

    public LiveData<List<Class>> getClasses(){
        return classes;
    }


}
