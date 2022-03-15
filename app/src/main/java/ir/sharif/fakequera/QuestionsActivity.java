package ir.sharif.fakequera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.viewModels.ClassViewModel;
import ir.sharif.fakequera.viewModels.QuestionViewModel;

public class QuestionsActivity extends AppCompatActivity {


    private ListView lv;
    private TextView txtTeacherName;

    private QuestionViewModel viewModel;
    private int classId = 0;
    private int ownerTeacherId = 0;
    private final ArrayList<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        initViews();
        checkArguments();
        initList();
        listeners();

        viewModel.questionList(classId);
        viewModel.teacher(ownerTeacherId);

        viewModel.getTeacherLiveData().observe(this , teacher -> {
            if (teacher == null){
                txtTeacherName.setText("Teacher Not Found !");
            }else {
                txtTeacherName.setText("Teacher Name : " + teacher.firstName + " " + teacher.lastName);
            }
        });

        viewModel.getMessage().observe(this , s -> {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });



    }

    private void initViews() {
        lv = findViewById(R.id.lv);
        txtTeacherName = findViewById(R.id.txtTeacherName);
    }

    private void checkArguments(){
        classId = getIntent().getIntExtra("class_id" , 0);
        ownerTeacherId = getIntent().getIntExtra("owner_teacher_id" , 0);
    }

    private void listeners() {
        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this , QuestionActivity.class);
            intent.putExtra("question_id" , questionList.get(i).uid);
            startActivity(intent);
        });
    }

    private void initList() {
        viewModel.getQuestionList().observe(this , list -> {

            ArrayList<String> contents = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                contents.add(list.get(i).content);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contents);
            lv.setAdapter(adapter);

            questionList.clear();
            questionList.addAll(list);

        });
    }
}