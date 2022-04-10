package ch.keepcalm.demo.application

import ch.keepcalm.demo.domain.TaskCreatedEvent
import ch.keepcalm.demo.domain.TaskDoneEvent
import ch.keepcalm.demo.domain.TaskId
import ch.keepcalm.demo.domain.TaskState
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class TaskAggregateTests {

    @Test
    fun `create a new task will expect a TaskCreatedEvent`() {
        val fixture = AggregateTestFixture(TaskAggregate::class.java)
        val now = LocalDateTime.now()
        val validValue = UUID.randomUUID().toString()
        val taskId = TaskId(validValue)
        fixture
            .`when`(CreateTaskCommand(taskId = taskId, date = now))
            .expectEvents(TaskCreatedEvent(taskId = taskId, date = now, state = TaskState.Open))
    }

    @Test
    fun `given a TaskCreatedEvent set task to DONE  will expect a TaskDoneEvent` () {
        val fixture = AggregateTestFixture(TaskAggregate::class.java)
        val now = LocalDateTime.now()
        val validValue = UUID.randomUUID().toString()
        val taskId = TaskId(validValue)
        fixture
            .given(TaskCreatedEvent(taskId = taskId, date = now, state = TaskState.Open))
            .`when`(DoneTaskCommand(taskId = taskId, date = now))
            .expectEvents(TaskDoneEvent(
                taskId = taskId, date =now, state =TaskState.Done))

    }
}
