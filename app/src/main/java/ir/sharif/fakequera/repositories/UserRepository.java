package ir.sharif.fakequera.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Objects;

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
    private List<User> all;

    private static volatile UserRepository INSTANCE;

    public static UserRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(application);
                }
            }
        }
        return INSTANCE;
    }


    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        studentDao = db.studentDao();
        teacherDao = db.teacherDao();
        currentUser = new MutableLiveData<>();
        currentTeacher = new MutableLiveData<>();
        currentStudent = new MutableLiveData<>();
        message = new MutableLiveData<>("Something went wrong!");
        AppDatabase.databaseWriteExecutor.execute(() -> all = userDao.all());
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
            all = userDao.all();
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
            all = userDao.all();
        });


    }

    public void setCurrentUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.deactivateAllUsers();
            if (user == null) {
                currentUser.postValue(null);
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
        if (currentUser.getValue() == null) {
            loadCurrentUser();
        }
        if (currentUser.getValue() == null) {
            return null;
        }

        AppDatabase.databaseWriteExecutor.execute(() -> {
            Teacher teacher;

            teacher = teacherDao.getTeacher2(Objects.requireNonNull(currentUser.getValue()).getUserName());
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
            student = studentDao.getStudent2(Objects.requireNonNull(currentUser.getValue()).userName);
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

    public void signOut() {
        setCurrentUser(null);
        AppDatabase.databaseWriteExecutor.execute(userDao::deactivateAllUsers);
    }

    public MutableLiveData<Teacher> getTeacherData(int uid){
        AppDatabase.databaseWriteExecutor.execute(() ->{
            User user = userDao.findByUsername(uid);
            Teacher currentTeach = teacherDao.getTeacher2(user.getUserName());
            currentTeacher.postValue(currentTeach);
        });
        return currentTeacher;
    }

    public String getUserName(int id){
        for (User user : all) {
            if (user.uid == id){
                return user.firstName;
            }
        }
        return null;
    }

    public void requestTeacher(int uid) {
        System.out.println("user id is : " + uid);
        AppDatabase.databaseWriteExecutor.execute(() ->{
            User user = userDao.findByUsername(uid);
            Teacher currentTeach = teacherDao.getTeacher2(user.getUserName());
            System.out.println(currentTeach);
            currentTeacher.postValue(currentTeach);
        });
    }
}
