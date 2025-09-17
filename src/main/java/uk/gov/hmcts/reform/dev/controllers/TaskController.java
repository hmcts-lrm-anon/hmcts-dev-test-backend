package uk.gov.hmcts.reform.dev.controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import uk.gov.hmcts.reform.dev.models.Status;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
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
import java.time.LocalDateTime;

@RestController
@RequestMapping("/tasks")

public class TaskController {


    public TaskController(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    private final TaskRepository repository;

    @GetMapping
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = repository.findById(id);
        return task.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Task createTask(@Valid @RequestBody Task task) {
        return repository.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task updatedTask) {
        Optional<Task> currentTask = repository.findById(id);
        if (currentTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repository.save(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
