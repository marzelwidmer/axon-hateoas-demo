package ch.keepcalm.demo

import ch.keepcalm.demo.application.CreateTaskCommand
import ch.keepcalm.demo.application.DoneTaskCommand
import ch.keepcalm.demo.domain.Task
import ch.keepcalm.demo.domain.TaskId
import ch.keepcalm.demo.infrastructure.configuration.AxonSnapshotThresholdConfigurer
import kotlinx.coroutines.*
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.hateoas.support.WebStack
import org.springframework.web.server.adapter.ForwardedHeaderTransformer
import java.time.LocalDateTime
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue


@SpringBootApplication
@EnableHypermediaSupport(stacks = [WebStack.WEBFLUX], type = [EnableHypermediaSupport.HypermediaType.HAL])
@EnableConfigurationProperties(AxonSnapshotThresholdConfigurer::class)
class KbootTasksApplicationService

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    runApplication<KbootTasksApplicationService>(*args) {
        addInitializers(
            beans {
                profile("standalone") {
                    bean {
                        val commandGateway = ref<ReactorCommandGateway>()
                        ApplicationRunner {
                            runBlocking {
                               val (value, time) =  measureTimedValue {
                                    loadEvents(commandGateway)
                                }
                                println("it took $time ---------------------> to load $value events.")
                            }
                        }
                    }
                }
                bean<ForwardedHeaderTransformer>()
            }
        )
    }
}


suspend fun loadEvents(commandGateway: ReactorCommandGateway): Int {
    val count = 100
    repeat(count) { counter ->
        val taskId = TaskId(UUID.randomUUID().toString())
        val now = LocalDateTime.now()
        val createTaskCommand = CreateTaskCommand(taskId = taskId, date = now)
        val doneTaskCommand = DoneTaskCommand(taskId = taskId, date = now)
        withContext(Dispatchers.IO) {
            println("ApplicationRunner Send Command [$createTaskCommand] : -----------------> $counter ")
            commandGateway.send<Any>(createTaskCommand).awaitSingleOrNull()
            println("ApplicationRunner Send Command [$doneTaskCommand] : -----------------> $counter ")
            commandGateway.send<Any>(doneTaskCommand).awaitSingleOrNull()
        }
        commandGateway.send<Any>(CreateTaskCommand(taskId = TaskId(UUID.randomUUID().toString()), date = now)).awaitSingleOrNull()
    }
    return count
}
