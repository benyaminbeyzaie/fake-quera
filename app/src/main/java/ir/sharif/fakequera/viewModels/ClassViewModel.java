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
    private final LiveData<List<Class>> allClasses;
    private final LiveData<List<Class>> studentClasses;
    private final LiveData<Boolean> userIsInClass;
    private final LiveData<String> message;

    public ClassViewModel(Application application) {
        super(application);
        repository = new ClassRepository(application);
        allClasses = repository.getAllClasses();
        studentClasses = repository.getStudentClasses();
        message = repository.getMessage();
        userIsInClass = repository.getUserIsInClass();
    }
    public LiveData<List<Class>> getStudentClasses(){
        return studentClasses;
    }
    public LiveData<List<Class>> getAllClasses() {
        return allClasses;
    }


    public void checkUser(int classId, int userId) {
        repository.checkUserId(classId, userId);
    }

    public void addUserToClass(int classId, int userId) {
        repository.setClassToStudent(userId, classId);
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<Boolean> getUserIsInClass() {
        return userIsInClass;
    }


    public void loadStudentClasses(int studentId) {
        repository.loadStudentClasses(studentId);
    }

    public void loadAllClasses() {
        repository.loadAllClasses();
    }
}
