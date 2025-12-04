package com.jminiapp.examples.todo;

import com.jminiapp.core.adapters.JSONAdapter;

/**
 * JSON adapter for TodoItem serialization/deserialization.
 *
 * <p>This adapter enables the Todo app to import and export todo items
 * to/from JSON files. It leverages the framework's JSONAdapter interface which
 * provides default implementations for serialization using Gson.</p>
 *
 * <p>Example JSON format:</p>
 * <pre>
 * [
 *   {
 *     "id": 1,
 *     "description": "Buy groceries",
 *     "completed": false
 *   },
 *   {
 *     "id": 2,
 *     "description": "Finish homework",
 *     "completed": true
 *   }
 * ]
 * </pre>
 */
public class TodoJSONAdapter implements JSONAdapter<TodoItem> {

    @Override
    public Class<TodoItem> getstateClass() {
        return TodoItem.class;
    }
}
