package ch.keepcalm.demo.application

import TaskCreatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class TaskAggregate {

    @AggregateIdentifier
    private lateinit var taskId: String

    constructor() {}

    @CommandHandler
    constructor(command: CreateTaskCommand) {
    	if (!this::taskId.isInitialized) {
    		AggregateLifecycle.apply(TaskCreatedEvent(taskId = command.taskId, date = command.date))
    	}
    }

    @EventSourcingHandler
    fun on(event: TaskCreatedEvent){
        taskId = event.taskId
    }

}
