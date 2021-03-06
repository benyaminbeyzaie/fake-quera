package ir.sharif.fakequera.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ir.sharif.fakequera.dao.AnswerDao;
import ir.sharif.fakequera.dao.ClassDao;
import ir.sharif.fakequera.dao.QuestionDao;
import ir.sharif.fakequera.dao.StudentDao;
import ir.sharif.fakequera.dao.TeacherDao;
import ir.sharif.fakequera.dao.UserDao;
import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;

@Database(entities = {User.class, Student.class, Teacher.class, Class.class, Question.class, Answer.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract StudentDao studentDao();

    public abstract TeacherDao teacherDao();

    public abstract ClassDao classDao();

    public abstract QuestionDao questionDao();

    public abstract AnswerDao answerDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app-database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
