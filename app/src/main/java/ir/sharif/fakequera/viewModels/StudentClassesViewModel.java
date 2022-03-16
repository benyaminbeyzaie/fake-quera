package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.repositories.ClassRepository;

public class StudentClassesViewModel extends AndroidViewModel {
    private final ClassRepository repository;
    private final LiveData<List<Class>> classes;
    private final LiveData<String> message;

    public StudentClassesViewModel(Application application, int studentId) {
        super(application);
        repository = new ClassRepository(application);
        classes = repository.getClassesOfStudent(studentId);
        message = repository.getMessage();
    }

    public LiveData<List<Class>> getClasses() {
        return classes;
    }
}
