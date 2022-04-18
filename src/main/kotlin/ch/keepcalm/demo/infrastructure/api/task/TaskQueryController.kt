package ch.keepcalm.demo.infrastructure.api.task

import ch.keepcalm.demo.domain.TaskState
import ch.keepcalm.demo.infrastructure.api.IndexRootController
import ch.keepcalm.demo.infrastructure.api.REL.API_SEARCH_REL
import ch.keepcalm.demo.infrastructure.persistence.FindAllTaskWithState
import ch.keepcalm.demo.infrastructure.persistence.FindAllTasks
import ch.keepcalm.demo.infrastructure.persistence.FindTaskById
import ch.keepcalm.demo.infrastructure.persistence.mongodb.TaskView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/query")
class TaskQueryController(private val queryGateway: QueryGateway) {

    @GetMapping("/tasks", produces = [MediaTypes.HAL_JSON_VALUE])
    suspend fun getAllTasks(): CollectionModel<EntityModel<TaskView>> {
        val result = withContext(Dispatchers.IO) {
            queryGateway.query(FindAllTasks(), ResponseTypes.multipleInstancesOf(TaskView::class.java)).get()
        }
        return CollectionModel.wrap(result.asIterable())
            .add(linkTo(methodOn(IndexRootController::class.java).index()).withRel(IanaLinkRelations.PREV).toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).getAllTasks()).withSelfRel().toMono().awaitSingle())
    }

    @GetMapping("/tasks/{id}", produces = [MediaTypes.HAL_JSON_VALUE])
    suspend fun getTaskById(@PathVariable id: String): EntityModel<TaskView> {
        val task = withContext(Dispatchers.IO) {
            queryGateway.query(FindTaskById(taskId = id), ResponseTypes.instanceOf(TaskView::class.java)).get()
        }
        return EntityModel.of(task)
            .add(linkTo(methodOn(TaskQueryController::class.java).getTaskById(id)).withSelfRel().toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).search(taskState = null)).withRel(API_SEARCH_REL).toMono().awaitSingle())
    }


    @GetMapping("/tasks/search", produces = [MediaTypes.HAL_JSON_VALUE])
    suspend fun search(@RequestParam taskState: TaskState?): CollectionModel<EntityModel<TaskView>> {
        val tasks = withContext(Dispatchers.IO) {
            queryGateway.query(FindAllTaskWithState(taskState = checkNotNull(taskState)), ResponseTypes.multipleInstancesOf(TaskView::class.java)).get()
        }
        return CollectionModel.wrap(tasks.asIterable())
            .add(linkTo(methodOn(IndexRootController::class.java).index()).withRel(IanaLinkRelations.PREV).toMono().awaitSingle())
            .add(linkTo(methodOn(TaskQueryController::class.java).search(taskState)).withSelfRel().toMono().awaitSingle())

    }
}

