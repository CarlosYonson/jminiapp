---
sidebar_position: 2
---

# Todo Example Application

A comprehensive todo list application demonstrating CRUD operations and data persistence.

**Features:**
- Add, list, toggle, and delete todo items
- Completion status tracking
- JSON import/export functionality
- Interactive menu-driven interface
- Persistent storage between sessions

**Source Code:** [examples/todo](https://github.com/jminiapp/jminiapp/tree/main/examples/todo)

### Key Concepts Demonstrated

- CRUD operations (Create, Read, Update, Delete)
- List-based state management
- Interactive user interface design
- Data persistence with JSON serialization
- Error handling and input validation

### Quick Start

```bash
cd examples/todo
mvn clean install
mvn exec:java
```

### Code Highlights

**State Model:**
```java
public class TodoItem {
    private int id;
    private String description;
    private boolean completed;

    public TodoItem(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public void toggleCompleted() {
        this.completed = !this.completed;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        String status = completed ? "[X]" : "[ ]";
        return String.format("%s %d. %s", status, id, description);
    }
}
```

**Application:**
```java
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

    // Implementation details for addTodo, listTodos, toggleTodo, deleteTodo, etc.
}
```

**Bootstrap:**
```java
public static void main(String[] args) {
    JMiniAppRunner
        .forApp(TodoApp.class)
        .withState(TodoItem.class)
        .withAdapters(new TodoJSONAdapter())
        .named("Todo")
        .withResourcesPath("./")
        .run(args);
}
```

This example showcases more advanced usage of JMiniApp, including list management, user interaction patterns, and comprehensive data operations. It demonstrates how the framework scales from simple state management to complex CRUD applications.
