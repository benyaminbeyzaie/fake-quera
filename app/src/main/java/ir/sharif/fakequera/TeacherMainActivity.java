package ir.sharif.fakequera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.utils.ClassAdapter;

public class TeacherMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Class> classes;
    ClassAdapter classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_teacher_main);


        recyclerView = findViewById(R.id.recylcer);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeacherMainActivity.this));

        classAdapter = new ClassAdapter(classes, TeacherMainActivity.this);
        recyclerView.setAdapter(classAdapter);
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
            DialogFragment dialogFragment = new DialogFragment();
            dialogFragment.show(fragmentManager, "DialogFragment");
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public void addClass(String name) {
        // TODO add class to data base
    }
}