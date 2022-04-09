package ch.keepcalm.demo.infrastructure.persistence.mongodb

import ch.keepcalm.demo.domain.TaskState
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class TaskView(@Indexed @Id val taskId: String, val state: TaskState, val date: LocalDateTime)

