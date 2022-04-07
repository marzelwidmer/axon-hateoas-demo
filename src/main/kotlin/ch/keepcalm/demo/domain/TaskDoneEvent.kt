package ch.keepcalm.demo.domain

import java.time.LocalDateTime

data class TaskDoneEvent(val taskId: TaskId, val date: LocalDateTime, val state: TaskState)
