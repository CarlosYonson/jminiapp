# Todo Example

A simple todo list application demonstrating the JMiniApp framework.

## Overview

This example shows how to create a basic mini-app using JMiniApp core that manages a list of todo items. Users can add, list, toggle completion status, delete todos, and import/export the todo list through an interactive menu.

## Features

- **Add Todo**: Create new todo items with descriptions
- **List Todos**: View all todos with their completion status
- **Toggle Completion**: Mark todos as complete/incomplete
- **Delete Todo**: Remove todos from the list
- **Export to JSON**: Save the current todo list to a JSON file
- **Import from JSON**: Load todo list from a JSON file
- **Persistent State**: Todo list is maintained in the app state

## Project Structure

```
todo/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/todo/
    ├── TodoApp.java          # Main application class
    ├── TodoAppRunner.java    # Bootstrap configuration
    ├── TodoItem.java         # Todo item model
    └── TodoJSONAdapter.java  # JSON format adapter
```

## Key Components

### TodoItem
A model class that represents a single todo item with:
- `id`: Unique identifier for the todo
- `description`: Text description of the todo
- `completed`: Boolean indicating completion status
- `toggleCompleted()`: Method to toggle completion status

### TodoJSONAdapter
A format adapter that enables JSON import/export for `TodoItem`:
- Implements `JSONAdapter<TodoItem>` from the framework
- Registers with the framework during app bootstrap
- Provides automatic serialization/deserialization

### TodoApp
The main application class that extends `JMiniApp` and implements:
- `initialize()`: Set up the app and load existing todo list
- `run()`: Main loop displaying menu and handling user input
- `shutdown()`: Save the todo list before exiting
- Uses framework's `context.importData()` and `context.exportData()` for file operations

### TodoAppRunner
Bootstrap configuration that:
- Registers the `TodoJSONAdapter` with `.withAdapters()`
- Configures the app name and model class
- Launches the application

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root** (not the examples/todo directory):
```bash
mvn clean install
```

This will build both the jminiapp-core module and the todo example.

### Run the application

Option 1: Using Maven exec plugin (from the examples/todo directory)
```bash
cd examples/todo
mvn exec:java
```

Option 2: Using the packaged JAR (from the examples/todo directory)
```bash
cd examples/todo
java -jar target/todo-app.jar
```

Option 3: From the project root
```bash
cd examples/todo && mvn exec:java
```

## Usage Example

### Basic Operations

```
=== Todo App ===
Welcome to the Todo List App!
Starting with empty todo list

--- Todo List (0 items) ---
1. Add todo
2. List todos
3. Toggle todo complete/incomplete
4. Delete todo
5. Export to JSON file
6. Import from JSON file
7. Exit

Choose an option: 1
Enter todo description: Buy groceries
Added: [ ] 1. Buy groceries

--- Todo List (1 items) ---
1. Add todo
2. List todos
3. Toggle todo complete/incomplete
4. Delete todo
5. Export to JSON file
6. Import from JSON file
7. Exit

Choose an option: 1
Enter todo description: Finish homework
Added: [ ] 2. Finish homework

--- Todo List (2 items) ---
1. Add todo
2. List todos
3. Toggle todo complete/incomplete
4. Delete todo
5. Export to JSON file
6. Import from JSON file
7. Exit

Choose an option: 2
Your Todos:
------------
[ ] 1. Buy groceries
[ ] 2. Finish homework

Completed: 0/2
```

### Toggle Completion

```
--- Todo List (2 items) ---
1. Add todo
2. List todos
3. Toggle todo complete/incomplete
4. Delete todo
5. Export to JSON file
6. Import from JSON file
7. Exit

Choose an option: 3

Your Todos:
------------
[ ] 1. Buy groceries
[ ] 2. Finish homework

Completed: 0/2

Enter todo ID to toggle: 1
Updated: [X] 1. Buy groceries
```

### Export to JSON

```
--- Todo List (2 items) ---
1. Add todo
2. List todos
3. Toggle todo complete/incomplete
4. Delete todo
5. Export to JSON file
6. Import from JSON file
7. Exit

Choose an option: 5
Todos exported successfully to: Todo.json
```

The exported JSON file will look like:
```json
[
  {
    "id": 1,
    "description": "Buy groceries",
    "completed": true
  },
  {
    "id": 2,
    "description": "Finish homework",
    "completed": false
  }
]
```

### Import from JSON

```
--- Todo List (0 items) ---
1. Add todo
2. List todos
3. Toggle todo complete/incomplete
4. Delete todo
5. Export to JSON file
6. Import from JSON file
7. Exit

Choose an option: 6
Todos imported successfully from Todo.json!
Loaded 2 todo(s)
```

## Next Steps

Try extending this example by:
- Adding due dates to todos
- Implementing priority levels
- Adding search/filter functionality
- Adding CSV export functionality using the `CSVAdapter`
- Implementing undo/redo functionality
- Adding validation for duplicate todos
- Implementing auto-save functionality
- Adding categories/tags for todos