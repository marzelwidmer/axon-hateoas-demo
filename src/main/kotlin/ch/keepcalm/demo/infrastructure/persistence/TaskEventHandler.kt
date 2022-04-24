package ch.keepcalm.demo.infrastructure.persistence

import ch.keepcalm.demo.domain.TaskCreatedEvent
import ch.keepcalm.demo.domain.TaskDoneEvent
import ch.keepcalm.demo.infrastructure.persistence.mongodb.TaskView
import ch.keepcalm.demo.infrastructure.persistence.mongodb.TaskViewRepository
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component
import java.util.*


@Component
class TaskEventHandler(private val taskViewRepository: TaskViewRepository) {

    // effect
    @EventHandler
    fun on(event: TaskCreatedEvent) {
        taskViewRepository.save(TaskView(event.taskId.value, taskState = event.state, date = event.date))
    }


    @EventHandler
    fun handle(event: TaskDoneEvent) {
        taskViewRepository.save(
            TaskView(taskId = event.taskId.value, taskState = event.state, date = event.date)
        )
    }

    @QueryHandler
    fun findAllTasks(findAllTasks: FindAllTasks): List<TaskView> {
        return taskViewRepository.findAll()
    }


    @QueryHandler
    fun findById(findTaskById: FindTaskById): Optional<TaskView> {
        return taskViewRepository.findById(findTaskById.taskId)
    }


    @QueryHandler
    fun findAllTaskWithState(findAllTaskWithState: FindAllTaskWithState): List<TaskView> {
        return taskViewRepository.findTaskViewByTaskState(findAllTaskWithState.taskState)
    }
}
