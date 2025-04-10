package com.example.task41p;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.task41p.data.Task;
import com.example.task41p.data.TaskDatabase;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    private List<Task> tasks = new ArrayList<>();
    private final OnTaskClickListener listener;

    public TaskAdapter(OnTaskClickListener listener) {
        this.listener = listener;
    }

    public void setTasks(List<Task> taskList) {
        this.tasks = taskList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.title.setText(task.title);
        holder.dueDate.setText(task.dueDate);

        holder.itemView.setOnClickListener(v -> listener.onTaskClick(task));

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        TaskDatabase.getInstance(v.getContext()).taskDao().delete(task);
                        tasks.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, dueDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskItemTitle);
            dueDate = itemView.findViewById(R.id.taskItemDueDate);
        }
    }
}