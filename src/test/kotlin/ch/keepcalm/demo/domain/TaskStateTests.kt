package ch.keepcalm.demo.domain

import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class TaskStateTests {

    @Test
    fun `Should create Status with valid value Open`() {
        TaskState.Open.name `should be` "Open"
    }

    @Test
    fun `Should create Status with valid value Done`() {
        TaskState.Done.name `should be` "Done"
    }

    @Test
    fun `Should create Status with valid value Deadline reached`() {
        TaskState.DeadlineReached.name `should be` "DeadlineReached"
    }
}



