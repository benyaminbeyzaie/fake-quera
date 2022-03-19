package ir.sharif.fakequera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ir.sharif.fakequera.Fragments.AddQuestionDialogFragment;
import ir.sharif.fakequera.R;
import ir.sharif.fakequera.Fragments.RenameDialogFragment;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.utils.ClassManagmentAdapter;
import ir.sharif.fakequera.viewModels.QuestionViewModel;

public class ClassManagmentActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    private QuestionViewModel questionViewModel;
    private int classUid;
    ClassManagmentAdapter classManagmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_class_managment);

        Intent i = getIntent();
        this.classUid = i.getIntExtra("uid", 0);
        recyclerView = findViewById(R.id.recylcer2);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClassManagmentActivity.this));

        classManagmentAdapter = new ClassManagmentAdapter(ClassManagmentActivity.this);
        recyclerView.setAdapter(classManagmentAdapter);

        questionViewModel = new QuestionViewModel(getApplication());


        questionViewModel.getQuestionList().observe(this, questions -> {
            Log.d("mym", "set changed");
            classManagmentAdapter.setQuestions(questions);
        });
        questionViewModel.getClassQuestions(this.classUid);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_new_question_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_question) {
            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            AddQuestionDialogFragment addQuestionDialogFragment = new AddQuestionDialogFragment();
            addQuestionDialogFragment.show(fragmentManager, "AddQuesionDialogFragment");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void takeData(String name, String content) {
        Question question = new Question(classUid, name, content);
        questionViewModel.insert(question);
    }

    public void gotoQuestion(int uid) {
        Intent i3 = new Intent(ClassManagmentActivity.this, AnswersActivity.class);
        i3.putExtra("uid", uid);
        startActivity(i3);
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    public void renameAction(String currentName, int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("name", currentName);
        RenameDialogFragment renameDialogFragment = new RenameDialogFragment();
//        scoreDialoge.setPosition(position);
        renameDialogFragment.setArguments(bundle);
        renameDialogFragment.show(fragmentManager, "renameDialogFragment");
    }

    public void rename(String input, int position) {
        Question question = classManagmentAdapter.getQuestion(position);
        question.setQuestionName(input);
        questionViewModel.update(question);
    }
}