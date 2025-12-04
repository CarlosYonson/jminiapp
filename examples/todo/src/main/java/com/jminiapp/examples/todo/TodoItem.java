package com.jminiapp.examples.todo;

/**
 * Represents a single todo item with a unique ID, description, and completion status.
 */
public class TodoItem {
    private int id;
    private String description;
    private boolean completed;

    // Default constructor for JSON deserialization
    public TodoItem() {
    }

    public TodoItem(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void toggleCompleted() {
        this.completed = !this.completed;
    }

    @Override
    public String toString() {
        String status = completed ? "[X]" : "[ ]";
        return String.format("%s %d. %s", status, id, description);
    }
}
