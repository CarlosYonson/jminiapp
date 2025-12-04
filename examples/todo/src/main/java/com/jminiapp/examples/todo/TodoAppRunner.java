package com.jminiapp.examples.todo;

import com.jminiapp.core.engine.JMiniAppRunner;

/**
 * Runner class to launch the Todo application.
 */
public class TodoAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
                .forApp(TodoApp.class)
                .withState(TodoItem.class)
                .withAdapters(new TodoJSONAdapter())
                .named("Todo")
                .withResourcesPath("./")  // Store Todo.json in resources directory
                .run(args);
    }
}
