import java.time.LocalDateTime

data class TaskCreatedEvent(val taskId: String, val date: LocalDateTime)
