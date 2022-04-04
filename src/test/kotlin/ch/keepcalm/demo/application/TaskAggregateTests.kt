package ch.keepcalm.demo.application
import TaskCreatedEvent
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
        val taskId = UUID.randomUUID().toString()
        val now = LocalDateTime.now()

        fixture
            .`when`(CreateTaskCommand(taskId = taskId, date = now))
            .expectEvents(TaskCreatedEvent(taskId = taskId, date = now))
    }

}