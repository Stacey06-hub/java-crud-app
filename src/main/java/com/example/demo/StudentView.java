
package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("")
public class StudentView extends VerticalLayout {

    private final List<Student> students = new ArrayList<>();
    private final Grid<Student> grid = new Grid<>(Student.class);

    private final TextField nameField = new TextField("Name");
    private final TextField emailField = new TextField("Email");

    private long nextId = 1;

    public StudentView() {
        grid.setColumns("id", "name", "email");
        grid.setItems(students);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        Button addButton = new Button("Add student", event -> {
            String name = nameField.getValue().trim();
            String email = emailField.getValue().trim();

            if (name.isEmpty() || email.isEmpty()) {
                return;
            }

            students.add(new Student(nextId++, name, email));
            refreshGrid();
            clearFields();
        });

        Button loadButton = new Button("Load selected", event -> {
            Student selected = grid.asSingleSelect().getValue();

            if (selected != null) {
                nameField.setValue(selected.getName());
                emailField.setValue(selected.getEmail());
            }
        });

        Button updateButton = new Button("Update selected", event -> {
            Student selected = grid.asSingleSelect().getValue();

            if (selected == null) {
                return;
            }

            String name = nameField.getValue().trim();
            String email = emailField.getValue().trim();

            if (name.isEmpty() || email.isEmpty()) {
                return;
            }

            selected.setName(name);
            selected.setEmail(email);

            refreshGrid();
            clearFields();
        });

        Button deleteButton = new Button("Delete selected", event -> {
            Student selected = grid.asSingleSelect().getValue();

            if (selected != null) {
                students.remove(selected);
                refreshGrid();
                clearFields();
            }
        });

        add(
            new H1("Student Management"),
            nameField,
            emailField,
            addButton,
            loadButton,
            updateButton,
            deleteButton,
            grid
        );
    }

    private void refreshGrid() {
        grid.setItems(students);
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
    }
}

