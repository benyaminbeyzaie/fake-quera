package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.repositories.ClassRepository;
import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;
import ir.sharif.fakequera.repositories.ClassRepository;
import ir.sharif.fakequera.repositories.UserRepository;



public class ClassViewModel extends AndroidViewModel {

    private final ClassRepository classRepository;
    private LiveData<List<Class>> teacherClasses;
    private final LiveData<List<Class>> classList;
    private final LiveData<String> message;

    public ClassViewModel(@NonNull Application application, int uid) {
        super(application);

//        Log.d("mym" ,currentTeacher.getValue());
        classRepository = new ClassRepository(application, uid);
        teacherClasses = classRepository.getClasses();
        classList = classRepository.getClassList();
        message = classRepository.getMessage();
    }

    public ClassViewModel(@NonNull Application application) {
        super(application);

//        Log.d("mym" ,currentTeacher.getValue());
        classRepository = new ClassRepository(application);
        teacherClasses = classRepository.getClasses();
        classList = classRepository.getClassList();
        message = classRepository.getMessage();
    }


    public void insert(Class clas) {
        classRepository.insert(clas);
    }

    public void update(Class clas) {
        classRepository.update(clas);
    }

    public void delete(Class clas) {
        classRepository.delete(clas);
    }

    public LiveData<List<Class>> getTeacherClasses() {
        return teacherClasses;
    }

    public void update(int uid) {
        classRepository.update(uid);
        teacherClasses = classRepository.getClasses();
    }

    public LiveData<List<Class>> getClassList() {
        return classList;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public void classList(){
        classRepository.classList();
    }

}
