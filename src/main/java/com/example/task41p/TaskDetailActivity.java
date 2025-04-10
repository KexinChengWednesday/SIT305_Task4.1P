package com.example.task41p;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.task41p.data.Task;
import com.example.task41p.data.TaskDatabase;

public class TaskDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView title = findViewById(R.id.taskTitle);
        TextView desc = findViewById(R.id.taskDescription);
        TextView dueDate = findViewById(R.id.taskDueDate);
        Button editBtn = findViewById(R.id.btnEdit);

        int taskId = getIntent().getIntExtra("task_id", -1);
        Task task = null;
        for (Task t : TaskDatabase.getInstance(this).taskDao().getAllTasks()) {
            if (t.id == taskId) {
                task = t;
                break;
            }
        }

        if (task != null) {
            title.setText(task.title);
            desc.setText(task.description);
            dueDate.setText(task.dueDate);

            Task finalTask = task;
            editBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, AddEditTaskActivity.class);
                intent.putExtra("task_id", finalTask.id);
                startActivity(intent);
            });
        }
    }
}
