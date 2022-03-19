package ir.sharif.fakequera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import ir.sharif.fakequera.R;
import ir.sharif.fakequera.entities.Class;
import ir.sharif.fakequera.viewModels.ClassViewModel;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class StudentMainActivity extends AppCompatActivity {

    private ListView lv;
    private TabLayout tabLayout;
    private ClassViewModel viewModel;
    private UserViewModel userViewModel;
    private final ArrayList<Class> allClasses = new ArrayList<>();
    private final ArrayList<Class> joinedClasses = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        viewModel = new ViewModelProvider(this).get(ClassViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.authenticateWithSavedCredentials();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        initViews();
        initList();
        listeners();

        viewModel.getMessage().observe(this, s -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show());
    }

    private void initViews() {
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
        tabLayout = findViewById(R.id.studentTabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initList();
                listeners();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void listeners() {
        if (tabLayout.getSelectedTabPosition() == 0) {
            lv.setOnItemClickListener((adapterView, view, i, l) -> {
                Intent intent = new Intent(this, QuestionsActivity.class);
                intent.putExtra("class_id", allClasses.get(i).uid);
                intent.putExtra("owner_teacher_id", allClasses.get(i).ownerTeacherId);
                intent.putExtra("user_id", Objects.requireNonNull(userViewModel.getCurrentUser().getValue()).uid);

                startActivity(intent);
            });
        } else {
            lv.setOnItemClickListener((adapterView, view, i, l) -> {
                Intent intent = new Intent(this, QuestionsActivity.class);
                intent.putExtra("class_id", joinedClasses.get(i).uid);
                intent.putExtra("owner_teacher_id", joinedClasses.get(i).ownerTeacherId);
                intent.putExtra("user_id", Objects.requireNonNull(userViewModel.getCurrentUser().getValue()).uid);

                startActivity(intent);
            });
        }

    }

    private void initList() {
        System.out.println(tabLayout.getSelectedTabPosition());
        if (tabLayout.getSelectedTabPosition() == 0) {
            if (viewModel.getStudentClasses().hasObservers()) {
                viewModel.getStudentClasses().removeObservers(this);
            }

            viewModel.getAllClasses().observe(this, list -> {
                adapter.clear();

                ArrayList<String> classNames = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    classNames.add(list.get(i).className);
                }

                adapter.addAll(classNames);

                allClasses.clear();
                allClasses.addAll(list);
            });
            viewModel.loadAllClasses();
        } else if (tabLayout.getSelectedTabPosition() == 1) {
            if (viewModel.getAllClasses().hasObservers()) {
                viewModel.getAllClasses().removeObservers(this);
            }

            viewModel.getStudentClasses().observe(this, list -> {
                adapter.clear();

                ArrayList<String> classNames = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    classNames.add(list.get(i).className);
                }

                adapter.addAll(classNames);

                joinedClasses.clear();
                joinedClasses.addAll(list);
            });
            if (userViewModel.getCurrentUser().getValue() == null) {
                return;
            }
            viewModel.loadStudentClasses(userViewModel.getCurrentUser().getValue().uid);
        }
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
                .setNegativeButton("Just exit", (dialog, which) -> finishAffinity())
                .show();
    }

}