package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;
import ir.sharif.fakequera.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository repository;
    private final LiveData<User> currentUser;

    public UserViewModel (Application application) {
        super(application);
        repository = new UserRepository(application);
        currentUser = repository.getCurrentUser();
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void insertStudent(Student user) { repository.insertStudent(user); }

    public void insertTeacher(Teacher user) { repository.insertTeacher(user); }

    public void authenticate(String username, String password) {
        repository.authenticate(username, password);
    }
}
