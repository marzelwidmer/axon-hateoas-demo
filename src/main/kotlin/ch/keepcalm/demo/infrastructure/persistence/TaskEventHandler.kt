package ch.keepcalm.demo.infrastructure.persistence

import ch.keepcalm.demo.domain.TaskCreatedEvent
import ch.keepcalm.demo.domain.TaskDoneEvent
import ch.keepcalm.demo.infrastructure.persistence.mongodb.TaskView
import ch.keepcalm.demo.infrastructure.persistence.mongodb.TaskViewRepository
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class TaskEventHandler(private val taskViewRepository: TaskViewRepository) {

    @EventHandler
    fun on(event: TaskCreatedEvent) {
        taskViewRepository.save(TaskView(event.taskId.value, state = event.state, date = event.date))
    }


    @EventHandler
    fun handle(event: TaskDoneEvent) {
        taskViewRepository.save(
            TaskView(taskId = event.taskId.value, state = event.state, date = event.date)
        )
    }

    @QueryHandler
    fun answer(query: FindAllTasksQuery?): List<TaskView> {
        return taskViewRepository.findAll()
    }
}
