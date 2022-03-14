package ir.sharif.fakequera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.entities.User;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class MainActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    ConstraintLayout layout;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        layout = findViewById(R.id.main_layout);
        frameLayout = layout.findViewById(R.id.frame);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.frame, loginFragment);
        fragmentTransaction.commit();


        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getId());
                System.out.println(tab.getText());
                if (Objects.requireNonNull(tab.getText()).equals(getString(R.string.login))) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    LoginFragment loginFragment = new LoginFragment();
                    fragmentTransaction.replace(R.id.frame, loginFragment);
                    fragmentTransaction.commit();
                } else if (tab.getText().equals(getString(R.string.signup))) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    SignupFragment signupFragment = new SignupFragment();
                    fragmentTransaction.replace(R.id.frame, signupFragment);
                    fragmentTransaction.commit();
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


    public void takeStudentData(){
        Intent i = new Intent(MainActivity.this , StudentMainActivity.class);
        startActivity(i);
    }

    public void takeTeacherData(int uid){
        Intent i = new Intent(MainActivity.this , TeacherMainActivity.class);
        i.putExtra("uid" , uid);
        startActivity(i);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}