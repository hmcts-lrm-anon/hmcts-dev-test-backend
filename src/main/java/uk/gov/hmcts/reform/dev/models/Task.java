package uk.gov.hmcts.reform.dev.models;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    private Long id;
    @NotEmpty
    private String title;
    private String description; // optional field
    @NotNull
    private Status status;
    @FutureOrPresent
    private LocalDateTime dueDateTime;
}
