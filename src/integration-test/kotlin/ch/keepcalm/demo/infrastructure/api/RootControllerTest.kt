package ch.keepcalm.demo.infrastructure.api

import ch.keepcalm.security.test.WithMockCustomUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.hateoas.MediaTypes.HAL_JSON
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel
import org.springframework.restdocs.hypermedia.HypermediaDocumentation.links
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@Import(value = [IndexRootController::class])
@WithMockCustomUser(username = "jane@doe.ch", authorities = ["keepcalm.admin", "keepcalm.user"], firstname = "jane", lastname = "doe")
class RootControllerTest(private var webTestClient: WebTestClient) {

    @BeforeEach
    fun setUp(applicationContext: ApplicationContext?, restDocumentation: RestDocumentationContextProvider?) {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext!!)
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
    fun `Should give back the index controller`() {
        webTestClient.get()
            .uri("/")
            .exchange()
            .expectHeader().contentType(HAL_JSON)
            .expectStatus().isOk
            .expectBody()
            .consumeWith(
                document(
                    "index",
                    links(
                        linkWithRel("self").description("Canonical link for this resource"),
                        linkWithRel("documentation").description("The <<resources_documentation,API Documentation>> resource"),
                        linkWithRel("foobar").description("The <<resources_documentation,API FooBar Resource>> resource"),
                    ), responseFields(
                        subsectionWithPath("_links").description("Links to other resources")
                    )
                )
            )
    }
}
