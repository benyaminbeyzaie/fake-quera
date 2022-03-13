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

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.utils.ClassAdapter;

public class TeacherMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Class> classes;
    ClassAdapter classAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_teacher_main);


        recyclerView = findViewById(R.id.recylcer);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeacherMainActivity.this));

        Class c1 = new Class("A");
        Class c2 = new Class("B");
        Class c3 = new Class("C");
        Class c4 = new Class("D");
        classes = new ArrayList<>();
        classes.add(c1);
        classes.add(c2);
        classes.add(c3);
        classes.add(c4);
//        classes = (ArrayList<Class>) Arrays.asList(c1,c2,c3,c4);
        classAdapter = new ClassAdapter(classes ,TeacherMainActivity.this);
        recyclerView.setAdapter(classAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.top_menu:
                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager =getSupportFragmentManager();
                 DialogFragment dialogFragment = new DialogFragment();
                 dialogFragment.show(fragmentManager , "DialogFragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void takeDate(String name){
        Class classs = new Class(name);
        classes.add(classs);
        classAdapter.setClasses(classes);
    }
}