package ch.keepcalm.demo.infrastructure.api.task

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class CreateTaskResource(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val createdDate: LocalDate
)
