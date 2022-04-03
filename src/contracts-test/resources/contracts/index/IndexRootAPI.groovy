package contracts.index

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "Index Root API entry point."
	name "index_root_api"
	request {
		method "GET"
		urlPath("/")
	}
	response {
		status OK()
		headers {
			contentType "application/hal+json"
		}
		body(
				[
						"_links": [
								"self"         : [
										"href": value(
												stub("http://${fromRequest().header('Host')}${fromRequest().header('Port')}/"),
												test("http://localhost:8080/"))
								],
								"documentation": [
										"href": value(
												stub("http://${fromRequest().header('Host')}${fromRequest().header('Port')}/api-docs/manual.html"),
												test("http://localhost:8080/api-docs/manual.html"))
								]
						]
				]
		)
	}
}
