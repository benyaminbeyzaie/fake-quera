package ir.sharif.fakequera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ir.sharif.fakequera.Fragments.AddClassDialogFragment;
import ir.sharif.fakequera.R;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.utils.ClassAdapter;
import ir.sharif.fakequera.viewModels.ClassViewModel;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class TeacherMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CardView cardView;
    TextView username;
    TextView university;
    TextView name;
    TextView classNumber;

    private ClassViewModel classViewModel;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_teacher_main);

        Intent i = getIntent();
        uid = i.getIntExtra("uid", 0);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.authenticateWithSavedCredentials();
        cardView = findViewById(R.id.answerCardView);
        username = cardView.findViewById(R.id.textViewStuName);
        university = cardView.findViewById(R.id.textViewStuAnswer);
        name = cardView.findViewById(R.id.textViewName);
        classNumber = cardView.findViewById(R.id.textViewClassNumber);

        LiveData<Teacher> data = userViewModel.getTeacherData(uid);

        data.observe(this, teacher -> {
            if (teacher == null) return;
            username.setText(teacher.userName);
            university.setText(teacher.universityName);
            name.setText(teacher.firstName);
        });


        recyclerView = findViewById(R.id.recylcer);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeacherMainActivity.this));

        ClassAdapter classAdapter = new ClassAdapter(TeacherMainActivity.this);

        recyclerView.setAdapter(classAdapter);

        classViewModel = new ClassViewModel(getApplication(), uid);
        classViewModel.getTeacherClasses().observe(this, classes -> {
            Log.d("mym", "changed");
            Log.d("mym", classes.toString());
            classAdapter.setClasses(classes);
            classNumber.setText("# Of classes : ".concat(classes.size() + ""));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.top_menu) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            AddClassDialogFragment dialogFragment = new AddClassDialogFragment();
            dialogFragment.show(fragmentManager, "DialogFragment");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToQuestion(int uid) {
        Intent i2 = new Intent(TeacherMainActivity.this, ClassManagmentActivity.class);
        i2.putExtra("uid", uid);
        startActivity(i2);
    }


    public void takeDate(String name) {
        Class classs = new Class(uid, name);
        Log.d("mym", classs.toString());
        classViewModel.insert(classs);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.logoutMessage)
                .setMessage(R.string.logoutWarning)
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                })
                .setNegativeButton("Just exit", (dialog, which) -> finishAffinity())
                .show();
    }
}