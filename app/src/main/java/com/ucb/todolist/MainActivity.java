package com.ucb.todolist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText todoEditText;
    private Button addButton;
    private ListView todoListView;
    private ArrayList<String> todoList;
    private ArrayAdapter<String> adapter;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        todoEditText = findViewById(R.id.todoEditText);
        addButton = findViewById(R.id.addButton);
        todoListView = findViewById(R.id.todoListView);

        // Initialize the ArrayList and adapter
        todoList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.todolist_item, R.id.todoTextView, todoList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);
                CheckBox checkBox = itemView.findViewById(R.id.todoCheckBox);
                ImageView imageView = itemView.findViewById(R.id.todoImageView);

                // Set up checkbox listener or other functionalities here if needed

                return itemView;
            }
        };

        // Set the adapter to the ListView
        todoListView.setAdapter(adapter);

        // Add Button click listener
        addButton.setOnClickListener(v -> {
            String task = todoEditText.getText().toString().trim();
            if (!task.isEmpty()) {
                todoList.add(task);
                adapter.notifyDataSetChanged();
                todoEditText.setText(""); // Clear the EditText after adding
            }
        });

        // Initialize GestureDetector
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // Get the tapped item position
                int position = todoListView.pointToPosition((int) e.getX(), (int) e.getY());
                if (position != ListView.INVALID_POSITION) {
                    showEditDeleteDialog(position);
                }
                return true;
            }
        });

        // Set touch listener for double tap detection
        todoListView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    // Method to show the dialog for editing or deleting a task
    private void showEditDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");

        String[] options = {"Edit", "Delete", "Cancel"};
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0: // Edit
                    showEditDialog(position);
                    break;
                case 1: // Delete
                    deleteTask(position);
                    break;
                case 2: // Cancel
                    dialog.dismiss();
                    break;
            }
        });

        builder.show();
    }

    // Method to show the edit dialog
    private void showEditDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        final EditText input = new EditText(this);
        input.setText(todoList.get(position));
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String editedTask = input.getText().toString().trim();
            if (!editedTask.isEmpty()) {
                todoList.set(position, editedTask);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // Method to delete a task
    private void deleteTask(int position) {
        todoList.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
    }
}
