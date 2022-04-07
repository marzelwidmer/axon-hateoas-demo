package ch.keepcalm.demo.application

import ch.keepcalm.demo.domain.TaskId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class DoneTaskCommand(@TargetAggregateIdentifier val taskId: TaskId, val date: LocalDateTime)




