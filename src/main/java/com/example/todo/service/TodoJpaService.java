package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoJpaRepository;
import com.example.todo.repository.TodoRepository;

@Service
public class TodoJpaRepository implements TodoRepository {

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @Override
    public ArrayList<Todo> getTodos() {
        List<Todo> todoList = todoJpaRepository.findAll();
        ArrayList<Todo> todos = new ArrayList<>(todoList);
        return todos;
    }

    @Override
    public Todo getTodoById(int id) {
        try {
            Todo todo = todoRepository.findById(id).get();
            return todo;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Todo addTodo(Todo todo) {
        todoJpaRepository.save(todo);
        return todo;
    }

    @Override
    public Todo updateTodo(int id, Todo todo) {
        try {
            Todo newTodo = todoJpaRepository.findById(id).get();
            if (todo.getTodo() != null) {
                newTodo.setTodo(todo.getTodo());
            }
            if (todo.getStatus() != null) {
                newTodo.setStatus(todo.getStatus());
            }
            if (todo.getPriority() != null) {
                newTodo.setPriority(todo.getPriority());
            }
            todoJpaRepository.save(newTodo);
            return newTodo;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteTodo(int id) {
        try {
            todoJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}