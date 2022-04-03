package ch.keepcalm.demo.infrastructure.api

import kotlinx.coroutines.reactive.awaitSingle
import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@InfrastructureRing
@RestController
class IndexRootController {

    companion object REL {
        const val API_DOCS_REL = "documentation"
    }

    @GetMapping("/", produces = [MediaTypes.HAL_JSON_VALUE])
    suspend fun index(): EntityModel<Unit> {
        return EntityModel.of(Unit, linkTo(methodOn(IndexRootController::class.java).index()).withSelfRel().toMono().awaitSingle())
            .add(linkTo(methodOn(IndexRootController::class.java).index()).slash("/api-docs/manual.html").withRel(API_DOCS_REL).toMono().awaitSingle())
    }
}
