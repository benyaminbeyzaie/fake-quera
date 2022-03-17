package ir.sharif.fakequera.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.repositories.ClassRepository;


public class ClassViewModel extends AndroidViewModel {

    private ClassRepository classRepository;
    private LiveData<List<Class>> teacherClasses;
    private LiveData<List<Class>> classList;
    private LiveData<String> message;

    private LiveData<List<Class>> allClasses;
    private LiveData<List<Class>> studentClasses;
    private LiveData<Boolean> userIsInClass;

    public ClassViewModel(@NonNull Application application, int uid) {
        super(application);

//        Log.d("mym" ,currentTeacher.getValue());
        classRepository = new ClassRepository(application, uid);
        teacherClasses = classRepository.getTeacherClasses(uid);
        classList = classRepository.getClassList();
        message = classRepository.getMessage();
    }

    public ClassViewModel(@NonNull Application application) {
        super(application);
        classRepository = new ClassRepository(application);
        allClasses = classRepository.getAllClasses();
        studentClasses = classRepository.getStudentClasses();
        message = classRepository.getMessage();
        userIsInClass = classRepository.getUserIsInClass();
    }


    public void insert(Class clas) {
        classRepository.insert(clas);
    }

    public void update(Class clas) {
        classRepository.update(clas);
    }

    public void delete(Class clas) {
        classRepository.delete(clas);
    }

    public LiveData<List<Class>> getTeacherClasses() {
        return teacherClasses;
    }

    public void update(int uid) {
        classRepository.update(uid);
        teacherClasses = classRepository.getTeacherClasses(uid);
    }

    public LiveData<List<Class>> getClassList() {
        return classList;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public void classList() {
        classRepository.classList();
    }

    public LiveData<List<Class>> getStudentClasses() {
        return studentClasses;
    }

    public LiveData<List<Class>> getAllClasses() {
        return allClasses;
    }

    public void checkUser(int classId, int userId) {
        classRepository.checkUserId(classId, userId);
    }

    public void addUserToClass(int classId, int userId) {
        classRepository.setClassToStudent(userId, classId);
    }

    public LiveData<Boolean> getUserIsInClass() {
        return userIsInClass;
    }


    public void loadStudentClasses(int studentId) {
        classRepository.loadStudentClasses(studentId);
    }

    public void loadAllClasses() {
        classRepository.loadAllClasses();
    }
}
