package ir.sharif.fakequera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ir.sharif.fakequera.dao.UserDao;
import ir.sharif.fakequera.database.AppDatabase;
import ir.sharif.fakequera.entities.User;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class MainActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    private RelativeLayout layout;
    TextInputLayout usernameInput;
    TextInputLayout passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.main_layout);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // TODO: delete this line and add sign up page
        userViewModel.insert(new User("ben", "123"));
        userViewModel.getCurrentUser().observe(this, user -> {
            if (user == null) {
                showTopSnackBar("User authenticated failed");
                return;
            }
            if (user.isCurrentUser) {
                showTopSnackBar("User authenticated successfully");
            } else {
                showTopSnackBar("User authenticated failed");
            }
        });
        findViewById(R.id.LoginButton).setOnClickListener(v -> {
            Log.d("user", "try with:" + Objects.requireNonNull(usernameInput.getEditText()).getText().toString());
            userViewModel.authenticate(Objects.requireNonNull(usernameInput.getEditText()).getText().toString(),
                    Objects.requireNonNull(passwordInput.getEditText()).getText().toString());
        });

    }

    void showTopSnackBar(String message) {
        Snackbar snack = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }
}