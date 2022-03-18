package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;
import ir.sharif.fakequera.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {

    public final UserRepository repository;
    private final LiveData<User> currentUser;
    private final LiveData<Teacher> currentTeacher;
    private final LiveData<String> message;

    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        currentUser = repository.getCurrentUser();
        currentTeacher = repository.getCurrentTeacher();
        message = repository.getMessage();
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<String> getMessage() {
        return message;
    }


    public void insertStudent(Student user) {
        repository.insertStudent(user);
    }

    public void insertTeacher(Teacher user) {
        repository.insertTeacher(user);
    }

    public void authenticate(String username, String password) {
        repository.authenticate(username, password);
    }

    public void authenticateWithSavedCredentials() {
        repository.loadCurrentUser();
    }

    public LiveData<Teacher> getCurrentTeacher() {
        return currentTeacher;
    }

    public void close() {
        repository.close();
    }

    public void signOut() {
        repository.signOut();
    }

    public LiveData<Teacher> getTeacherData(int uid) {
        return repository.getTeacherData(uid);
    }

    public String getUserName(int uid) {
        return repository.getUserName(uid);
    }
}
