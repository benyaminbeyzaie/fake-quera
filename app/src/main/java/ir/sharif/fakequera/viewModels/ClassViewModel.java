package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.repositories.ClassRepository;

public class ClassViewModel extends AndroidViewModel {

    private final ClassRepository classRepository;
    private LiveData<List<Class>> teacherClasses;

    public ClassViewModel(@NonNull Application application, int uid) {
        super(application);

//        Log.d("mym" ,currentTeacher.getValue());
        classRepository = new ClassRepository(application, uid);
        teacherClasses = classRepository.getClasses();
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

}
