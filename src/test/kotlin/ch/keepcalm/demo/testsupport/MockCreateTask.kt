package ch.keepcalm.demo.testsupport

import ch.keepcalm.demo.infrastructure.api.task.CreateTaskResource
import java.time.LocalDate


object MockCreateTask {

    val createTaskValidValues = CreateTaskResource(
        createdDate = LocalDate.of(2016, 4 ,27)
    )

}
