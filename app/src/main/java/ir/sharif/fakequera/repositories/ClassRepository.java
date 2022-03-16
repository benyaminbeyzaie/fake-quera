package ir.sharif.fakequera.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.sharif.fakequera.dao.ClassDao;
import ir.sharif.fakequera.dao.StudentDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Class;

public class ClassRepository {
    private final ClassDao classDao;
    private final StudentDao studentDao;
    private final MutableLiveData<String> message;
    private final MutableLiveData<List<Class>> classes;

    public ClassRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        classDao = db.boxDao();
        studentDao = db.studentDao();
        classes = new MutableLiveData<>();
        message = new MutableLiveData<>("Loading");
    }

    public LiveData<List<Class>> getClassesOfStudent(int studentUId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Class> classesList = classDao.getClassesOfStudent(studentUId);
            classes.postValue(classesList);
            message.postValue("classes are loaded");
        });
        return classes;
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

    public LiveData<String> getMessage() {
        return message;
    }


}
