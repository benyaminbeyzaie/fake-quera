package ir.sharif.fakequera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.utils.ClassManagmentAdapter;
import ir.sharif.fakequera.viewModels.QuestionViewModel;

public class ClassManagmentActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    private QuestionViewModel questionViewModel;
    private int classUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_class_managment);

        Intent i = getIntent();
        this.classUid = i.getIntExtra("uid", 0);
        recyclerView = findViewById(R.id.recylcer2);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClassManagmentActivity.this));

        ClassManagmentAdapter classManagmentAdapter = new ClassManagmentAdapter();
        recyclerView.setAdapter(classManagmentAdapter);

        questionViewModel = new QuestionViewModel(getApplication(), classUid);
        questionViewModel.getClassQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                classManagmentAdapter.setQuestions(questions);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_new_question_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        if (item.getItemId() == R.id.add_question) {
//            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
////            FragmentManager fragmentManager = getSupportFragmentManager();
////            DialogFragment dialogFragment = new DialogFragment();
////            dialogFragment.show(fragmentManager, "DialogFragment");
//            return true;
//        }
        return super.onOptionsItemSelected(item);

    }

}