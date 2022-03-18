package ir.sharif.fakequera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import ir.sharif.fakequera.entities.Class;
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
//        questionViewModel = new QuestionViewModel(getApplication(), classUid);


        questionViewModel.getQuestionList().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                Log.d("mym" , "set changed");
                classManagmentAdapter.setQuestions(questions);
            }
        });
        questionViewModel.getClassQuestions(this.classUid);

//        LiveData<List<Question>> classQuestions = questionViewModel.getClassQuestions(this.classUid);
//        classQuestions.observe(this, new Observer<List<Question>>() {
//            @Override
//            public void onChanged(List<Question> questions) {
//
//                classManagmentAdapter.setQuestions(questions);
//            }
//        });
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
            AddQuesionDialogFragment addQuesionDialogFragment = new AddQuesionDialogFragment();
            addQuesionDialogFragment.show(fragmentManager , "AddQuesionDialogFragment");
//            DialogFragment dialogFragment = new DialogFragment();
//            dialogFragment.show(fragmentManager, "DialogFragment");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void takeData(String name , String content) {
        Question question = new Question(classUid , name , content);
        questionViewModel.insert(question);
    }

    public void gotoQuestion(int uid) {
        Intent i3 = new Intent(ClassManagmentActivity.this , AnswerPage.class);
        i3.putExtra("uid" , uid);
        startActivity(i3);
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    public void renameAction(String currentName , int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt("position" , position);
        bundle.putString("name" , currentName);
        RenameDialogFragment renameDialogFragment = new RenameDialogFragment();
//        scoreDialoge.setPosition(position);
        renameDialogFragment.setArguments(bundle);
        renameDialogFragment.show(fragmentManager, "renameDialogFragment");
    }

    public void rename(String input , int position) {
        Question question = classManagmentAdapter.getQuestion(position);
        question.setQuestionName(input);
        questionViewModel.update(question);
    }
}