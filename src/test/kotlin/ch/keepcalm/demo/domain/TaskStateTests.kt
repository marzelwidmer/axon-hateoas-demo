package ch.keepcalm.demo.domain

import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class TaskStateTests {

    @Test
    fun `Should create Status with valid value Open`() {
        TaskState.Open.name `should be` "Open"
    }


}



