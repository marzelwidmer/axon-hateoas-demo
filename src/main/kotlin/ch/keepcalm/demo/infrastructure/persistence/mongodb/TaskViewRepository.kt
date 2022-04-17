package ch.keepcalm.demo.infrastructure.persistence.mongodb

import ch.keepcalm.demo.domain.TaskState
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TaskViewRepository : MongoRepository<TaskView, String> {

    fun findTaskViewByTaskState(taskState: TaskState) : List<TaskView>
}
