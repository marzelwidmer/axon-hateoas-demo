package ch.keepcalm.demo.domain

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class TaskTests {

    @Test
    fun `Should create Task`() {
        val task = Task("123")
        val taskId = task.taskId
        task.taskId `should be equal to` taskId
    }

}



