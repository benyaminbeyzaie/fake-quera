package ir.sharif.fakequera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import ir.sharif.fakequera.entities.User;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class MainActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.main_layout);

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


        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getId());
                System.out.println(tab.getText());
                if (Objects.requireNonNull(tab.getText()).equals(getString(R.string.login))){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    LoginFragment loginFragment = new LoginFragment();
                    fragmentTransaction.add(R.id.frame , loginFragment);
                    fragmentTransaction.commit();

                    Toast.makeText(MainActivity.this, "login", Toast.LENGTH_SHORT).show();

                }else if (tab.getText().equals(getString(R.string.signup))){
                    Toast.makeText(MainActivity.this, "signup", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
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