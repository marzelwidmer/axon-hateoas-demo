package ch.keepcalm.demo.infrastructure.api.task

import ch.keepcalm.demo.infrastructure.api.IndexRootController
import ch.keepcalm.demo.infrastructure.persistence.FindAllTasks
import ch.keepcalm.demo.infrastructure.persistence.FindTaskById
import ch.keepcalm.demo.infrastructure.persistence.mongodb.TaskView
import kotlinx.coroutines.reactor.awaitSingle
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/query")
class TaskQueryController(private val queryGateway: QueryGateway) {

    @GetMapping("/tasks", produces = [MediaTypes.HAL_JSON_VALUE])
    suspend fun getAllTasks(): CollectionModel<EntityModel<TaskView>> {
        val result = queryGateway.query(FindAllTasks(), ResponseTypes.multipleInstancesOf(TaskView::class.java)).get()
        return CollectionModel.wrap(result.asIterable())
            .add(linkTo(methodOn(IndexRootController::class.java).index()).withRel(IanaLinkRelations.PREV).toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).getAllTasks()).withSelfRel().toMono().awaitSingle())
    }

    @GetMapping("/tasks/{id}", produces = [MediaTypes.HAL_JSON_VALUE])
    suspend fun getTaskById(@PathVariable id: String): EntityModel<TaskView> {
        val task = queryGateway.query(FindTaskById(taskId= id), ResponseTypes.instanceOf(TaskView::class.java)).get()
        return EntityModel.of(task)
            .add(linkTo(methodOn(TaskQueryController::class.java).getTaskById(id)).withSelfRel().toMono().awaitSingle())
    }
}

