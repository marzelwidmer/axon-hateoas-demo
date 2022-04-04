package ch.keepcalm.demo.infrastructure.configuration


import mu.KLogging
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class AxonEventLogger {

    companion object : KLogging()

    @EventHandler
    fun on(any: Any) {
        logger.info("::--> Handling event: {}", any.javaClass)
    }
}
