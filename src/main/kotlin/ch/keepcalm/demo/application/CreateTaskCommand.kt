package ch.keepcalm.demo.application

import ch.keepcalm.demo.domain.TaskId
import java.time.LocalDateTime

data class CreateTaskCommand(val taskId: TaskId, val date: LocalDateTime)




