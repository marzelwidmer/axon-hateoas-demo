package ch.keepcalm.demo.infrastructure.api.task

import ch.keepcalm.demo.application.CreateTaskCommand
import ch.keepcalm.demo.domain.TaskId
import com.fasterxml.jackson.annotation.JsonFormat
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/command")
class CreatTaskController(private val commandGateway: ReactorCommandGateway) {


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = ["/tasks"],
        produces = [MediaTypes.HAL_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun createTask(@RequestBody task: CreateTaskResource): EntityModel<Unit> {

        val taskId = TaskId(UUID.randomUUID().toString())
        val now = LocalDateTime.now()
        val createTaskCommand = CreateTaskCommand(taskId = taskId, date = now)

        // Axon
        commandGateway.send<Any>(createTaskCommand).awaitSingleOrNull()

        return  EntityModel.of(Unit)
            .add(linkTo(methodOn(CreatTaskController::class.java).createTask(
                CreateTaskResource(
                    createdDate = now.toLocalDate()
                )
            )).withSelfRel().toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).getAllTasks()).withRel(IanaLinkRelations.COLLECTION).toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).getTaskById(taskId.value)).withRel(IanaLinkRelations.CURRENT).toMono().awaitSingle())
    }
}


data class CreateTaskResource(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val createdDate: LocalDate
)
