package jrpricing.discount.infra.external

import jrpricing.discount.infra.external.exception.CatalogApiClientException
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class ExternalApiClient(
    private val restTemplate: RestTemplate
) {
    companion object {
        private val contentType = "Content-Type"
        private val contentTypeValue = "application/json"
    }

    fun callGet(builder: UriComponentsBuilder): String? {
        val headers = HttpHeaders()
        headers.set(contentType, contentTypeValue)

        val entity = HttpEntity<Any>(headers)

        val response: ResponseEntity<String> = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            String::class.java
        )

        if (response.statusCode == HttpStatus.NOT_FOUND) {
            return null
        }

        if (response.statusCode == HttpStatus.BAD_REQUEST) {
            return null
        }

        if (response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw CatalogApiClientException(response.toString(), response.statusCode)
        }

        return response.body
    }
}