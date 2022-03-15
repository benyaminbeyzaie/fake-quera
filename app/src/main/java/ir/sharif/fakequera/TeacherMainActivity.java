package ir.sharif.fakequera;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.utils.ClassAdapter;
import ir.sharif.fakequera.viewModels.ClassViewModel;

public class TeacherMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private ClassViewModel classViewModel;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_teacher_main);


        Intent i = getIntent();
        uid = i.getIntExtra("uid", 0);

        recyclerView = findViewById(R.id.recylcer);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeacherMainActivity.this));

        ClassAdapter classAdapter = new ClassAdapter(TeacherMainActivity.this);

        recyclerView.setAdapter(classAdapter);

        classViewModel = new ClassViewModel(getApplication(), uid);
        classViewModel.getTeacherClasses().observe(this, classes -> {
            Log.d("mym", classes.toString());
            classAdapter.setClasses(classes);
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
            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            AddClassDialogFragment dialogFragment = new AddClassDialogFragment();
            dialogFragment.show(fragmentManager, "DialogFragment");
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    
    public void goToQuestion(int uid){
        Intent i2 = new Intent(TeacherMainActivity.this , ClassManagmentActivity.class);
        i2.putExtra("uid" , uid);
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
                .setNegativeButton("No", null)
                .show();
    }


}