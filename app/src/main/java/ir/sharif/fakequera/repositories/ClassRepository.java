package ir.sharif.fakequera.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ir.sharif.fakequera.dao.ClassDao;
import ir.sharif.fakequera.dao.QuestionDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;

public class ClassRepository {
    private final ClassDao classDao;
    private final QuestionDao questionDao;
    private final MutableLiveData<List<Class>> classList;
    private final MutableLiveData<String> message;

    public ClassRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        classDao = db.classDao();
        questionDao = db.questionDao();

        classList = new MutableLiveData<>();
        message = new MutableLiveData<>();


        // fake data
//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            if (classDao.all().size() == 0) {
//                for (int i = 0; i < 10; i++) {
//
//                    classDao.insert(new Class("Class " + i));
//                }
//                for (int i = 0; i < 10; i++) {
//
//                    questionDao.insert(new Question(1, "Question  " + i));
//                }
//            }
//        });
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
}
