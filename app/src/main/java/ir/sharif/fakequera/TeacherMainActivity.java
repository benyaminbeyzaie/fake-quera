package ir.sharif.fakequera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.repositories.ClassRepository;
import ir.sharif.fakequera.repositories.UserRepository;
import ir.sharif.fakequera.utils.ClassAdapter;
import ir.sharif.fakequera.viewModels.ClassViewModel;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class TeacherMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private ClassViewModel classViewModel;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_teacher_main);


        Intent i = getIntent();
        uid = i.getIntExtra("uid" , 0);

        recyclerView = findViewById(R.id.recylcer);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeacherMainActivity.this));

        ClassAdapter classAdapter = new ClassAdapter();

        recyclerView.setAdapter(classAdapter);

        classViewModel = new ClassViewModel(getApplication() , uid);
        classViewModel.getTeacherClasses().observe(this, new Observer<List<Class>>() {
            @Override
            public void onChanged(List<Class> classes) {
                Log.d("mym", classes.toString());
                classAdapter.setClasses(classes);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.top_menu:
                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.show(fragmentManager, "DialogFragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void takeDate(String name) {
        Class classs = new Class(uid , name);
        Log.d("mym" , classs.toString());
        classViewModel.insert(classs);
    }

}