package ch.keepcalm.demo.domain

import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import java.util.*

class TaskIdTests {

    @Test
    fun `Should create TaskId with valid values`() {
        val validValue = UUID.randomUUID().toString()
        val taskId = TaskId(validValue)
        taskId `should not be` null
        taskId.toString() `should be` validValue
    }

}



