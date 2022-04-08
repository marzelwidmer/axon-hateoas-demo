package ch.keepcalm.demo.infrastructure.api

import ch.keepcalm.demo.infrastructure.persistence.FindAllTasksQuery
import ch.keepcalm.demo.infrastructure.persistence.mongodb.TaskView
import kotlinx.coroutines.reactive.awaitSingle
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/query")
class TaskQueryController(private val queryGateway: QueryGateway) {

    @GetMapping("/tasks", produces = [MediaTypes.HAL_JSON_VALUE])
    suspend fun getAllTasks(): CollectionModel<EntityModel<TaskView>> {
        val result = queryGateway.query(FindAllTasksQuery(), ResponseTypes.multipleInstancesOf(TaskView::class.java)).get()
        return CollectionModel.wrap(result.asIterable())
            .add(linkTo(methodOn(IndexRootController::class.java).index()).withRel(IanaLinkRelations.PREV).toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).getAllTasks()).withSelfRel().toMono().awaitSingle())
    }
}

