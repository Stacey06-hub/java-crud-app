
package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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

        Button addButton = new Button("Add student", event -> addStudent());

        Button deleteButton = new Button("Delete selected", event -> {
            Student selected = grid.asSingleSelect().getValue();
            if (selected != null) {
                students.remove(selected);
                grid.setItems(students);
            }
        });

        add(new com.vaadin.flow.component.html.H1("Student Management"));
        add(nameField, emailField, addButton, deleteButton, grid);
    }

    private void addStudent() {
        String name = nameField.getValue();
        String email = emailField.getValue();

        if (name == null || name.isBlank()
                || email == null || email.isBlank()) {
            return;
        }

        students.add(new Student(nextId++, name, email));
        grid.setItems(students);

        nameField.clear();
        emailField.clear();
    }
}
