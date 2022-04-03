package ch.keepcalm.demo.base

import ch.keepcalm.demo.DemoApplicationService
import ch.keepcalm.security.test.WithMockCustomUser
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@SpringBootTest(
    classes = [DemoApplicationService::class], webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = ["spring.main.web-application-type=reactive", "server.port=8080"]
)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@WithMockCustomUser(username = "jane@doe.ch", authorities = ["keepcalm.admin", "keepcalm.user"], firstname = "jane", lastname = "doe")
abstract class BaseClass {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider?) {
        runBlocking {
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
    }
}
