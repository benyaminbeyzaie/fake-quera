package ir.sharif.fakequera.repositories;

import android.app.Application;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ir.sharif.fakequera.dao.ClassDao;
import ir.sharif.fakequera.dao.QuestionDao;
import ir.sharif.fakequera.dao.StudentDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Class;

public class ClassRepository {
    private  ClassDao classDao;
    private  QuestionDao questionDao;
    private  StudentDao studentDao;
    private  MutableLiveData<List<Class>> allClasses;
    private  MutableLiveData<List<Class>> studentClasses;
    private  MutableLiveData<String> message;
    private  MutableLiveData<Boolean> userIsInClass;
    private  MutableLiveData<List<Class>> classList;


    private UserRepository userRepository;
    private LiveData<List<Class>> teacherClasses;
    private int uid;


    public ClassRepository(Application application , int uid) {
        AppDatabase db = AppDatabase.getDatabase(application);
        classDao = db.classDao();
        questionDao = db.questionDao();
        userRepository = UserRepository.getInstance(application);
        classList = new MutableLiveData<>();
        message = new MutableLiveData<>();
        this.uid = uid;
        teacherClasses = classDao.getClassesOfTeacher(uid);
    }

    public ClassRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        classDao = db.classDao();
        questionDao = db.questionDao();
        allClasses = new MutableLiveData<>();
        message = new MutableLiveData<>();
        studentDao = db.studentDao();
        studentClasses = new MutableLiveData<>();
        userIsInClass = new MutableLiveData<>();
    }


    public void classList() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Class> list = classDao.all();
            if (list == null || list.isEmpty()) {
                message.postValue("Class Not Found !");
                return;
            }

            classList.postValue(list);
        });
    }

    public MutableLiveData<List<Class>> getClassList() {
        return classList;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void insert(Class clas){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            classDao.insert(clas);
        });
    }

    public void update(Class clas) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            classDao.update(clas);
        });
    }

    public void delete(Class clas) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            classDao.delete(clas);
        });
    }

    public LiveData<List<Class>> getTeacherClasses(int uid) {
        AppDatabase.databaseWriteExecutor.execute(() ->{
            teacherClasses = classDao.getClassesOfTeacher(uid);

//            List<Class> classesOfTeacher = classDao.getClassesOfTeacher(uid);
//            teacherClasses.postValue(classesOfTeacher);
            message.postValue("classes are loaded");
        });
        return teacherClasses;
    }

    public void update(int uid) {
        Log.d("mym", "update uid" + uid);
        this.uid = uid;
        loadTeacherClasses(uid);
    }

    public void loadTeacherClasses(int teacherUid){
        AppDatabase.databaseWriteExecutor.execute(() ->{
            teacherClasses = classDao.getClassesOfTeacher(teacherUid);
//            teacherClasses.postValue(classesOfTeacher);
            message.postValue("classes are loaded");
        });
    }

    public void loadStudentClasses(int studentUId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Class> classesList = classDao.getClassesOfStudent(studentUId);
            studentClasses.postValue(classesList);
            message.postValue("classes are loaded");
        });
    }

    public void setClassToStudent(int studentUId, int classUId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Class aClass = classDao.getClassWithUId(classUId);
            if (aClass.students == null) {
                aClass.students = new ArrayList<>();
            }
            aClass.students.add(studentUId);
            classDao.update(aClass);
            message.postValue("class is set to student");
            loadStudentClasses(studentUId);
            checkUserId(classUId, studentUId);
        });
    }


    public void loadAllClasses() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Class> list = classDao.all();
            if (list == null || list.isEmpty()) {
                message.postValue("Class Not Found !");
                return;
            }
            allClasses.postValue(list);
        });
    }

    public LiveData<List<Class>> getAllClasses() {
        loadAllClasses();
        return allClasses;
    }

    public void checkUserId(int classId, int userId) {
        userIsInClass.postValue(false);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Class aClass = classDao.getClassWithUId(classId);
            if (aClass.students == null) {
                userIsInClass.postValue(false);
                return;
            }
            if (aClass.students.contains(userId)) {
                userIsInClass.postValue(true);
                return;
            }
            userIsInClass.postValue(false);
        });
    }

    public LiveData<List<Class>> getStudentClasses() {
        return studentClasses;
    }

        public LiveData<Boolean> getUserIsInClass() {
        return userIsInClass;
    }
}
