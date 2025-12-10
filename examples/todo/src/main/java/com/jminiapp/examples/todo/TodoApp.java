package com.jminiapp.examples.todo;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple todo list application demonstrating the JMiniApp framework.
 *
 * This app allows users to:
 * - Add new todo items
 * - List all todos
 * - Mark todos as complete/incomplete
 * - Delete todos
 * - Import/export todos to JSON files
 */
public class TodoApp extends JMiniApp {
    private Scanner scanner;
    private List<TodoItem> todos;
    private boolean running;
    private int nextId;

    public TodoApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        System.out.println("\n=== Todo App ===");
        System.out.println("Welcome to the Todo List App!");

        scanner = new Scanner(System.in);
        running = true;

        // Try to load existing todos from context
        List<TodoItem> data = context.getData();
        if (data != null && !data.isEmpty()) {
            todos = new ArrayList<>(data);
            // Find the next available ID
            nextId = todos.stream()
                    .mapToInt(TodoItem::getId)
                    .max()
                    .orElse(0) + 1;
            System.out.println("Loaded " + todos.size() + " existing todo(s)");
        } else {
            todos = new ArrayList<>();
            nextId = 1;
            System.out.println("Starting with empty todo list");
        }
    }

    @Override
    protected void run() {
        while (running) {
            displayMenu();
            handleUserInput();
        }
    }

    @Override
    protected void shutdown() {
        // Save the todos to context
        context.setData(todos);

        scanner.close();
        System.out.println("\nSaving " + todos.size() + " todo(s)...");
        System.out.println("Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\n--- Todo List (" + todos.size() + " items) ---");
        System.out.println("1. Add todo");
        System.out.println("2. List todos");
        System.out.println("3. Toggle todo complete/incomplete");
        System.out.println("4. Delete todo");
        System.out.println("5. Export to JSON file");
        System.out.println("6. Import from JSON file");
        System.out.println("7. Exit");
        System.out.print("\nChoose an option: ");
    }

    private void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    addTodo();
                    break;

                case "2":
                    listTodos();
                    break;

                case "3":
                    toggleTodo();
                    break;

                case "4":
                    deleteTodo();
                    break;

                case "5":
                    exportToFile();
                    break;

                case "6":
                    importFromFile();
                    break;

                case "7":
                    running = false;
                    System.out.println("\nExiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1-7.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addTodo() {
        System.out.print("Enter todo description: ");
        String description = scanner.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("Error: Description cannot be empty");
            return;
        }

        TodoItem todo = new TodoItem(nextId++, description);
        todos.add(todo);
        System.out.println("Added: " + todo);
    }

    private void listTodos() {
        if (todos.isEmpty()) {
            System.out.println("\nNo todos yet! Add one to get started.");
            return;
        }

        System.out.println("\nYour Todos:");
        System.out.println("------------");
        for (TodoItem todo : todos) {
            System.out.println(todo);
        }

        long completedCount = todos.stream().filter(TodoItem::isCompleted).count();
        System.out.println("\nCompleted: " + completedCount + "/" + todos.size());
    }

    private void toggleTodo() {
        if (todos.isEmpty()) {
            System.out.println("No todos to toggle!");
            return;
        }

        listTodos();
        System.out.print("\nEnter todo ID to toggle: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            TodoItem todo = findTodoById(id);

            if (todo != null) {
                todo.toggleCompleted();
                System.out.println("Updated: " + todo);
            } else {
                System.out.println("Todo with ID " + id + " not found");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format");
        }
    }

    private void deleteTodo() {
        if (todos.isEmpty()) {
            System.out.println("No todos to delete!");
            return;
        }

        listTodos();
        System.out.print("\nEnter todo ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            TodoItem todo = findTodoById(id);

            if (todo != null) {
                todos.remove(todo);
                System.out.println("Deleted todo: " + todo.getDescription());
            } else {
                System.out.println("Todo with ID " + id + " not found");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format");
        }
    }

    private void exportToFile() {
        try {
            // Save current todos to context before exporting
            context.setData(todos);

            // Use default filename convention: {appName}.{format}
            context.exportData("json");
            System.out.println("Todos exported successfully to: Todo.json");
        } catch (IOException e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            // Use default filename convention: {appName}.{format}
            context.importData("json");

            // Update local todos from context after import
            List<TodoItem> data = context.getData();
            if (data != null && !data.isEmpty()) {
                todos = new ArrayList<>(data);
                // Update nextId
                nextId = todos.stream()
                        .mapToInt(TodoItem::getId)
                        .max()
                        .orElse(0) + 1;
                System.out.println("Todos imported successfully from Todo.json!");
                System.out.println("Loaded " + todos.size() + " todo(s)");
            } else {
                System.out.println("Error: No data found in file.");
            }
        } catch (IOException e) {
            System.out.println("Error importing file: " + e.getMessage());
        }
    }

    private TodoItem findTodoById(int id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
