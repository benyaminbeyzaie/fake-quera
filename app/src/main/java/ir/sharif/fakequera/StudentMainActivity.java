package ir.sharif.fakequera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.utils.QueraSnackbar;
import ir.sharif.fakequera.viewModels.ClassViewModel;

public class StudentMainActivity extends AppCompatActivity {

    private ListView lv;

    private ClassViewModel viewModel;

    private final ArrayList<Class> classList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        viewModel = new ViewModelProvider(this).get(ClassViewModel.class);

        initViews();
        initList();
        listeners();

        viewModel.classList();

        viewModel.getMessage().observe(this , s -> {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });
    }

    private void initViews() {
        lv = findViewById(R.id.lv);
    }

    private void listeners() {
        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this , QuestionsActivity.class);
            intent.putExtra("class_id" , classList.get(i).uid);
            intent.putExtra("owner_teacher_id" , classList.get(i).ownerTeacherId);
            startActivity(intent);
        });
    }

    private void initList() {
        viewModel.getClassList().observe(this , list -> {

            ArrayList<String> classNames = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                classNames.add(list.get(i).className);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classNames);
            lv.setAdapter(adapter);

            classList.clear();
            classList.addAll(list);

        });
    }
}