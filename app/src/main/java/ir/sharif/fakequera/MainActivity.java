package ir.sharif.fakequera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ir.sharif.fakequera.dao.UserDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         final ExecutorService loader =
                Executors.newFixedThreadPool(2);
         // TODO: use view model and better thread model to handle this.
         loader.execute(() -> {
             AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

             UserDao userDao = db.userDao();
             userDao.insert(new User("benyamin", "123"));
             userDao.insert(new User("ali", "1234"));
             List<User> users = userDao.getAll();
             Log.d("users", users.toString());
         });
        setContentView(R.layout.activity_main);
    }
}