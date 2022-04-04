package ch.keepcalm.demo

import ch.keepcalm.demo.application.command.CreateTaskCommand
import ch.keepcalm.demo.domain.TaskId
import ch.keepcalm.demo.infrastructure.configuration.AxonSnapshotThresholdConfigurer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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


@SpringBootApplication
@EnableHypermediaSupport(stacks = [WebStack.WEBFLUX], type = [EnableHypermediaSupport.HypermediaType.HAL])
@EnableConfigurationProperties(AxonSnapshotThresholdConfigurer::class)
class KbootTasksApplicationService

fun main(args: Array<String>) {
    runApplication<KbootTasksApplicationService>(*args) {
        addInitializers(
            beans {
                bean {
                    val commandGateway = ref<ReactorCommandGateway>()
                    ApplicationRunner {
                        runBlocking {
                            repeat(500) {
                                val taskId = TaskId(UUID.randomUUID().toString())
                                val now = LocalDateTime.now()
                                commandGateway.send<Any>(CreateTaskCommand(taskId = taskId, date = now)).awaitSingleOrNull()
                                println("ApplicationRunner Send Command : -----------------> $it")
                            }
                        }
                    }
                }
                bean<ForwardedHeaderTransformer>()
            }
        )
    }
}
