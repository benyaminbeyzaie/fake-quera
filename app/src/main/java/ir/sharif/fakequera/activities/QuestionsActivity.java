package ir.sharif.fakequera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import ir.sharif.fakequera.R;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.viewModels.ClassViewModel;
import ir.sharif.fakequera.viewModels.QuestionViewModel;

public class QuestionsActivity extends AppCompatActivity {


    private ListView lv;
    private TextView txtTeacherName;
    private Button button;

    private QuestionViewModel questionViewModel;
    private ClassViewModel classViewModel;

    private int classId = 0;
    private int ownerTeacherId = 0;
    private int userId;
    private final ArrayList<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        classViewModel = new ViewModelProvider(this).get(ClassViewModel.class);

        initViews();
        checkArguments();
        initList();
        listeners();

        classViewModel.checkUser(classId, userId);

        classViewModel.getUserIsInClass().observe(this, value -> {
            if (value) {
                button.setVisibility(View.GONE);
            } else {
                button.setVisibility(View.VISIBLE);
            }
        });

        questionViewModel.questionList(classId);
        questionViewModel.teacher(ownerTeacherId);

        questionViewModel.getTeacherLiveData().observe(this, teacher -> {
            if (teacher == null) {
                txtTeacherName.setText(R.string.not_found_teacher);
            } else {
                txtTeacherName.setText(new StringBuilder().append("Teacher Name: ").append(teacher.firstName).append(" ").toString());
            }
        });

        questionViewModel.getMessage().observe(this, s -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show());
    }

    private void initViews() {
        lv = findViewById(R.id.lv);
        txtTeacherName = findViewById(R.id.txtTeacherName);
        button = findViewById(R.id.joinButton);

    }

    private void checkArguments() {
        classId = getIntent().getIntExtra("class_id", 0);
        ownerTeacherId = getIntent().getIntExtra("owner_teacher_id", 0);
        userId = getIntent().getIntExtra("user_id", 0);
    }

    private void listeners() {
        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, AnswerActivity.class);
            intent.putExtra("question_id", questionList.get(i).uid);
            startActivity(intent);
        });

        button.setOnClickListener(v -> classViewModel.addUserToClass(classId, userId));
    }

    private void initList() {
        questionViewModel.getQuestionList().observe(this, list -> {

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