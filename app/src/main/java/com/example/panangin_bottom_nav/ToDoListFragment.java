package com.example.panangin_bottom_nav;

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
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ToDoListFragment extends Fragment {

    private EditText todoEditText;
    private Button addButton;
    private ListView todoListView;
    private ArrayList<String> todoList;
    private ArrayAdapter<String> adapter;
    private GestureDetector gestureDetector; // Declare the GestureDetector

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todolist_fragment, container, false);

        todoEditText = view.findViewById(R.id.todoEditText);
        addButton = view.findViewById(R.id.addButton);
        todoListView = view.findViewById(R.id.todoListView);

        // Initialize the ArrayList and adapter
        todoList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getContext(), R.layout.todo_list_item, R.id.todoTextView, todoList) {
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
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = todoEditText.getText().toString().trim();
                if (!task.isEmpty()) {
                    todoList.add(task);
                    adapter.notifyDataSetChanged();
                    todoEditText.setText(""); // Clear the EditText after adding
                }
            }
        });

        // Initialize GestureDetector
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
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

        return view;
    }

    // Method to show the dialog for editing or deleting a task
    private void showEditDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Task");

        final EditText input = new EditText(getContext());
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
        Toast.makeText(getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
    }
}
