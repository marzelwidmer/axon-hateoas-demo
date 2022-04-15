package ch.keepcalm.demo.infrastructure.api

import ch.keepcalm.demo.infrastructure.api.task.CreatTaskController
import ch.keepcalm.demo.infrastructure.api.task.CreateTaskResource
import ch.keepcalm.demo.infrastructure.api.task.TaskQueryController
import kotlinx.coroutines.reactive.awaitSingle
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime

@InfrastructureRing
@RestController
class IndexRootController {

    companion object REL {
        const val API_DOCS_REL = "documentation"
        const val API_GET_ALL_TASKS_REL = "get-all-tasks"
        const val API_CREATE_NEW_TASK_REL = "create-new-task"
    }

    @GetMapping("/", produces = [MediaTypes.HAL_JSON_VALUE])
    suspend fun index(): EntityModel<Unit> {
        return EntityModel.of(Unit, linkTo(methodOn(IndexRootController::class.java).index()).withSelfRel().toMono().awaitSingle())
            .add(linkTo(methodOn(IndexRootController::class.java).index()).slash("/api-docs/manual.html").withRel(API_DOCS_REL).toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).getAllTasks()).withRel(API_GET_ALL_TASKS_REL).toMono().awaitSingle())
            .add(linkTo(methodOn(CreatTaskController::class.java).createTask(
                CreateTaskResource(
                    createdDate = LocalDate.now()
                ))
            ).withRel(API_CREATE_NEW_TASK_REL).toMono().awaitSingle())
    }
}
