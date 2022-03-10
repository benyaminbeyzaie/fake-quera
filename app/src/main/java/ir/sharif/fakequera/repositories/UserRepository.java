package ir.sharif.fakequera.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ir.sharif.fakequera.dao.UserDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.User;

public class UserRepository {
    private final UserDao userDao;
    private final MutableLiveData<User> currentUser;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        currentUser = new MutableLiveData<>();
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public void setCurrentUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (user == null) {
                currentUser.postValue(user);
                return;
            }
            user.isCurrentUser = true;
            userDao.updateUser(user);
            currentUser.postValue(user);
        });
    }

    public void authenticate(String userName, String password) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            final User user = userDao.findByUsername(userName);
            if (user == null) {
                setCurrentUser(null);
                return;
            }
            if (user.password.equals(password)) {
                setCurrentUser(user);
            }
        });

    }

    public LiveData<User> getCurrentUser() {
        if (currentUser == null) {
            loadCurrentUser();
        }
        return currentUser;
    }

    public void loadCurrentUser() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user;
            user = userDao.getCurrentUser();
            if (user == null) {
                return;
            }
            currentUser.postValue(user);
        });
    }
}
