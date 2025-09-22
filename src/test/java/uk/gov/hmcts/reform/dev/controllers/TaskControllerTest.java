
package uk.gov.hmcts.reform.dev.controllers;

import uk.gov.hmcts.reform.dev.models.Task;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskRepository repository;

    @Test
    void getAllTasks_returnsEmptyList() throws Exception {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void getTaskById_returnsTask_whenFound() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setStatus(uk.gov.hmcts.reform.dev.models.Status.OPEN);
        task.setDueDateTime(java.time.LocalDateTime.now().plusDays(5));
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(task));
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Task"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OPEN"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dueDateTime").exists());
    }

    @Test
    void getTaskById_returnsNotFound_whenNotFound() throws Exception {
        when(repository.findById(99L)).thenReturn(java.util.Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createTask_returnsCreatedTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("New Task");
        task.setStatus(uk.gov.hmcts.reform.dev.models.Status.OPEN);
        task.setDueDateTime(LocalDateTime.now().plusDays(3));

        when(repository.save(any(Task.class))).thenReturn(task);

        String json = String.format("""
            {
                "id": 1,
                "title": "New Task",
                "status": "OPEN",
                "dueDateTime": "%s"
            }
            """, getFutureDate(3));

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                .contentType("application/json")
                .content(json))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Task"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OPEN"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dueDateTime").exists());
    }

    @Test
    void updateTask_returnsUpdatedTask_whenFound() throws Exception {
        Task existing = new Task();
        existing.setId(2L);
        existing.setTitle("Old Title");
        Task updated = new Task();
        updated.setId(2L);
        updated.setTitle("Updated Title");
        when(repository.findById(2L)).thenReturn(java.util.Optional.of(existing));
        when(repository.save(org.mockito.ArgumentMatchers.any(Task.class))).thenReturn(updated);
        String json = String.format("""
            {
                "id": 2,
                "title": "Updated Title",
                "status": "OPEN",
                "dueDateTime": "%s"
            }
            """, getFutureDate(3));            
        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/2")
                .contentType("application/json")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void updateTask_returnsNotFound_whenNotFound() throws Exception {
        when(repository.findById(99L)).thenReturn(java.util.Optional.empty());
        String json = """
            {
                "id": 99,
                "title": "Owt",
                "status": "OPEN",
                "dueDateTime": "2025-12-31T23:59:59"
            }
            """;        
        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/99")
                .contentType("application/json")
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteTask_returnsNoContent_whenFound() throws Exception {
        when(repository.existsById(3L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/3"))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteTask_returnsNotFound_whenNotFound() throws Exception {
        when(repository.existsById(99L)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    private String getFutureDate(int daysAhead) {
        return LocalDateTime.now().plusDays(daysAhead)
            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }    
}
