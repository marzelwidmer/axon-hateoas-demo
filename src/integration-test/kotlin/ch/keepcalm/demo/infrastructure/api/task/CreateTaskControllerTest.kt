package ch.keepcalm.demo.infrastructure.api.task

import ch.keepcalm.demo.KbootTasksApplicationService
import ch.keepcalm.demo.testsupport.MockCreateTask
import ch.keepcalm.security.test.WithMockCustomUser
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.ApplicationContext
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class, RestDocumentationExtension::class)
@SpringBootTest(
    classes = [KbootTasksApplicationService::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.main.web-application-type=reactive", "spring.main.banner-mode=off"]
)
@WithMockCustomUser(username = "jane@doe.ch", authorities = ["keepcalm.admin", "keepcalm.user"], firstname = "jane", lastname = "doe")
class CreateTaskControllerTest(private var webTestClient: WebTestClient) {

    @SpyBean
    private lateinit var commandGateway: ReactorCommandGateway

    companion object {
        const val API_CREATE_TASK = "/command/tasks"
    }

    @BeforeEach
    fun setUp(applicationContext: ApplicationContext, restDocumentation: RestDocumentationContextProvider) {

        webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
            .configureClient()
            .filter(
                documentationConfiguration(restDocumentation)
                    .operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
            )
            .build()
    }

    @Test
    fun `should expected http status 201 created`() {
        webTestClient.post()
            .uri(API_CREATE_TASK)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaTypes.HAL_JSON)
            .bodyValue(MockCreateTask.createTaskValidValues)
            .exchange()
            .expectStatus()
            .isCreated
            .expectBody()
            .consumeWith(
                document(
                    "createTaskValidValues",
                )
            )
    }
}
