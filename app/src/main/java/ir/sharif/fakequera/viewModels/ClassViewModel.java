package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.repositories.ClassRepository;
import ir.sharif.fakequera.repositories.UserRepository;

public class ClassViewModel extends AndroidViewModel {

    private ClassRepository classRepository;
    private UserRepository userRepository;
    private LiveData<Class> teacherClasses;
    private MutableLiveData<Teacher> currentTeacher;

    public ClassViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        currentTeacher = (MutableLiveData<Teacher>) userRepository.getCurrentTeacher();
        classRepository = new ClassRepository(application , currentTeacher.getValue().uid);

    }


    public void insert(Class clas){
        classRepository.insert(clas);
    }

    public void update(Class clas){
        classRepository.update(clas);
    }

    public void delete(Class clas){
        classRepository.delete(clas);
    }

    public LiveData<List<Class>> getTeacherClasses(){
        return classRepository.getClasses();
    }
}
