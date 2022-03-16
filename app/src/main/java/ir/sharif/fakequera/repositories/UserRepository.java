package ir.sharif.fakequera.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import ir.sharif.fakequera.dao.StudentDao;
import ir.sharif.fakequera.dao.TeacherDao;
import ir.sharif.fakequera.dao.UserDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;

public class UserRepository {
    private final UserDao userDao;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;
    private final MutableLiveData<User> currentUser;
    private final MutableLiveData<Teacher> currentTeacher;
    private final MutableLiveData<Student> currentStudent;
    private final MutableLiveData<String> message;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        studentDao = db.studentDao();
        teacherDao = db.teacherDao();
        currentUser = new MutableLiveData<>();
        currentTeacher = new MutableLiveData<>();
        currentStudent = new MutableLiveData<>();
        message = new MutableLiveData<>("Something went wrong!");
    }

    public void insertTeacher(Teacher teacher) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (userDao.findByUsername(teacher.userName) != null) {
                message.postValue("This username is already exists!");
                setCurrentUser(null);
                return;
            }
            message.postValue("User registered as teacher");
            userDao.insertUser(teacher);
            teacherDao.insert(teacher);
            setCurrentUser(teacher);
        });
    }

    public void insertStudent(Student student) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (userDao.findByUsername(student.userName) != null) {
                message.postValue("This username is already exists!");
                setCurrentUser(null);
                return;
            }
            message.postValue("User registered as student");
            userDao.insertUser(student);
            studentDao.insert(student);
            setCurrentUser(student);
        });


    }

    public void setCurrentUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (user == null) {
                currentUser.postValue(null);
                return;
            }
            userDao.deactivateAllUsers();
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
            if (!user.password.equals(password)) {
                message.postValue("Password is incorrect");
                setCurrentUser(null);
                return;
            }
            message.postValue("User signed in successfully");
            setCurrentUser(user);
        });
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<User> getCurrentUser() {
        if (currentUser == null) {
            loadCurrentUser();
        }
        return currentUser;
    }


    public LiveData<Teacher> getCurrentTeacher() {
        if (currentUser == null) {
            loadCurrentUser();
        }
        if (currentUser == null) {
            return null;
        }
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Teacher teacher;
            teacher = teacherDao.getTeacher(Objects.requireNonNull(currentUser.getValue()).uid);
            currentTeacher.postValue(teacher);
        });
        return currentTeacher;
    }

    public LiveData<Student> getCurrentStudent() {
        if (currentUser == null) {
            loadCurrentUser();
        }
        if (currentUser == null) {
            return null;
        }
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Student student;
            student = studentDao.getStudent(Objects.requireNonNull(currentUser.getValue()).uid);
            currentStudent.postValue(student);
        });
        return currentStudent;
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
