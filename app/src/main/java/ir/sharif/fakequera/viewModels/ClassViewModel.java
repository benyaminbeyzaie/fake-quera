package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;
import ir.sharif.fakequera.repositories.ClassRepository;
import ir.sharif.fakequera.repositories.UserRepository;

public class ClassViewModel extends AndroidViewModel {

    public final ClassRepository repository;
    private final LiveData<List<Class>> classList;
    private final LiveData<String> message;

    public ClassViewModel(Application application) {
        super(application);
        repository = new ClassRepository(application);
        classList = repository.getClassList();
        message = repository.getMessage();
    }

    public LiveData<List<Class>> getClassList() {
        return classList;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public void classList(){
        repository.classList();
    }
}
