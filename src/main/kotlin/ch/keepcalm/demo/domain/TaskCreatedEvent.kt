package ch.keepcalm.demo.domain

import ch.keepcalm.demo.domain.TaskId
import java.time.LocalDateTime

data class TaskCreatedEvent (val taskId: TaskId, val date: LocalDateTime)
