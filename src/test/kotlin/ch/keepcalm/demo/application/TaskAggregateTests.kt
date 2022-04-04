package ch.keepcalm.demo.application

import ch.keepcalm.demo.application.command.CreateTaskCommand
import ch.keepcalm.demo.application.event.TaskCreatedEvent
import ch.keepcalm.demo.domain.TaskId
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class TaskAggregateTests {

    // Initialize aggregate test
    private val fixture: AggregateTestFixture<TaskAggregate> by lazy {
        AggregateTestFixture(TaskAggregate::class.java)
    }


    @Test
    fun `Create task command will expect a TaskCreatedEvent`() {
        val now = LocalDateTime.now()
        val validValue = UUID.randomUUID().toString()
        val taskId = TaskId(validValue)
        fixture
            .`when`(CreateTaskCommand(taskId = taskId, date = now))
            .expectEvents(TaskCreatedEvent(taskId = taskId, date = now))
    }

}
