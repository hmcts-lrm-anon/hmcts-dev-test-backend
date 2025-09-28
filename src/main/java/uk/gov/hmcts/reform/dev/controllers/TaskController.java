package uk.gov.hmcts.reform.dev.controllers;

import jakarta.validation.Valid;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uk.gov.hmcts.reform.dev.models.Task;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")

public class TaskController {


    public TaskController(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    private final TaskRepository repository;

    /**
     * Retrieves all tasks in the system.
     *
     * @return a list of all tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(repository.findAll());
    }

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id id of the task to get
     * @return the task if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = repository.findById(id);
        return task.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new task.
     *
     * @param task the task to create
     * @return the created task
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = repository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    /**
     * Updates an existing task.
     *
     * @param id id of the task to update
     * @param updatedTask the updated task data
     * @return the updated task if found, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task updatedTask) {
        Optional<Task> currentTask = repository.findById(id);
        if (currentTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repository.save(updatedTask));
    }

    /**
     * Deletes a task by its unique identifier.
     *
     * @param id id of the task to delete
     * @return 204 No Content if deleted, or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
