package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

    public void insert(User user) { repository.insert(user); }

    public void authenticate(String username, String password) {
        repository.authenticate(username, password);
    }
}
