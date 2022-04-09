package ch.keepcalm.demo.infrastructure.api.task

import ch.keepcalm.demo.application.CreateTaskCommand
import ch.keepcalm.demo.domain.TaskId
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
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/command")
class CreatTaskController(private val commandGateway: ReactorCommandGateway) {


    @PostMapping("/tasks", produces = [MediaTypes.HAL_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createTask(@RequestBody task: CreateTaskResource): EntityModel<Unit> {

        val taskId = TaskId(UUID.randomUUID().toString())
        val now = LocalDateTime.now()
        val createTaskCommand = CreateTaskCommand(taskId = taskId, date = now)

        // Axon
        commandGateway.send<Any>(createTaskCommand).awaitSingleOrNull()

        return  EntityModel.of(Unit)
            .add(linkTo(methodOn(CreatTaskController::class.java).createTask(CreateTaskResource())).withSelfRel().toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).getAllTasks()).withRel(IanaLinkRelations.COLLECTION).toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).getTaskById(taskId.value)).withRel(IanaLinkRelations.CURRENT).toMono().awaitSingle())
    }
}

