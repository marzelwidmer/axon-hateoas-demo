package ch.keepcalm.demo.application

import ch.keepcalm.demo.domain.TaskCreatedEvent
import ch.keepcalm.demo.domain.TaskDoneEvent
import ch.keepcalm.demo.domain.TaskId
import ch.keepcalm.demo.domain.TaskState
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate(snapshotTriggerDefinition = "mySnapshotTriggerDefinition")
class TaskAggregate {

    @AggregateIdentifier
    private lateinit var taskId: TaskId
    private lateinit var taskState: TaskState

    constructor() {}

    @CommandHandler
    constructor(command: CreateTaskCommand) {
        if (!this::taskId.isInitialized) {
            AggregateLifecycle.apply(TaskCreatedEvent(taskId = command.taskId, date = command.date, state = TaskState.Open))
    	}
    }

    @EventSourcingHandler
    fun on(event: TaskCreatedEvent){
        taskId = event.taskId
        taskState = event.state
    }


    @CommandHandler
    fun handle(command: DoneTaskCommand){
    	AggregateLifecycle.apply(TaskDoneEvent(taskId = command.taskId, date = command.date, state = TaskState.Done))
    }

    @EventSourcingHandler
    fun on(event: TaskDoneEvent){
        taskState = event.state
    }
}
