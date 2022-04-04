package ch.keepcalm.demo.domain

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test

class TaskTests {

    @Test
    fun `Should create Task with TaskId`() {
        val validValue = "1234"
        val task = Task(TaskId(validValue))
        task `should not be` null

        val taskId = task.taskId
        task.taskId `should be equal to` taskId
    }

}



