package com.example.task41p;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.task41p.data.Task;
import com.example.task41p.data.TaskDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ImageButton addBtn = findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_tasks) {
                    return true;
                } else if (item.getItemId() == R.id.nav_about) {
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    private void loadTasks() {
        List<Task> taskList = TaskDatabase.getInstance(this).taskDao().getAllTasks();
        adapter.setTasks(taskList);
    }

    @Override
    public void onTaskClick(Task task) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra("task_id", task.id);
        startActivity(intent);
    }
}
