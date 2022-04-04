package ch.keepcalm.demo.application

import java.time.LocalDateTime

data class CreateTaskCommand(val taskId: String, val date: LocalDateTime)
