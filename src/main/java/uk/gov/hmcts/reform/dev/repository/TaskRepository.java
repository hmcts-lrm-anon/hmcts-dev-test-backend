package uk.gov.hmcts.reform.dev.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.Status;
import uk.gov.hmcts.reform.dev.models.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends ListCrudRepository<Task, Long> {
    List<Task> findAllByStatus(Status status);

    List<Task> findAllByDueDateTimeBefore(LocalDateTime dueDateTime);
}
