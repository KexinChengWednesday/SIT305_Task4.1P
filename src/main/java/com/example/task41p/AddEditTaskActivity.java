package com.example.task41p;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.task41p.data.Task;
import com.example.task41p.data.TaskDatabase;

public class AddEditTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        EditText titleInput = findViewById(R.id.inputTitle);
        EditText descriptionInput = findViewById(R.id.inputDescription);
        EditText dueDateInput = findViewById(R.id.inputDueDate);
        Button saveBtn = findViewById(R.id.btnSave);

        int taskId = getIntent().getIntExtra("task_id", -1);
        Task taskToEdit = null;

        if (taskId != -1) {
            for (Task t : TaskDatabase.getInstance(this).taskDao().getAllTasks()) {
                if (t.id == taskId) {
                    taskToEdit = t;
                    break;
                }
            }

            if (taskToEdit != null) {
                titleInput.setText(taskToEdit.title);
                descriptionInput.setText(taskToEdit.description);
                dueDateInput.setText(taskToEdit.dueDate);
            }
        }

        Task finalTaskToEdit = taskToEdit;
        saveBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String desc = descriptionInput.getText().toString();
            String date = dueDateInput.getText().toString();

            if (title.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Title and due date are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (finalTaskToEdit != null) {
                finalTaskToEdit.title = title;
                finalTaskToEdit.description = desc;
                finalTaskToEdit.dueDate = date;
                TaskDatabase.getInstance(this).taskDao().update(finalTaskToEdit);
                Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
            } else {
                Task task = new Task(title, desc, date);
                TaskDatabase.getInstance(this).taskDao().insert(task);
                Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}
