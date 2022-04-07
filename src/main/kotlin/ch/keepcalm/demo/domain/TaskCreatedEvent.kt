package ch.keepcalm.demo.domain

import java.time.LocalDateTime

data class TaskCreatedEvent(val taskId: TaskId, val date: LocalDateTime, val state: TaskState)
