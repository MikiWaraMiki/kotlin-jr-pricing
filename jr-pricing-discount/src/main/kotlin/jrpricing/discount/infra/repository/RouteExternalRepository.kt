package jrpricing.discount.infra.repository

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jrpricing.discount.domain.route.BusinessKilometer
import jrpricing.discount.domain.route.Route
import jrpricing.discount.domain.route.RouteRepository
import jrpricing.discount.infra.external.ExternalApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.util.UriComponentsBuilder

private data class RouteSearchResponse(
    val id: String,
    val distance: Int,
    val departureStation: Map<String, String>,
    val arrivalStation: Map<String, String>
)

class RouteExternalRepository(
    private val externalApiClient: ExternalApiClient,
    @Value("\${jr-discount-app-conf.catalogBaseUrl") private val catalogUrl: String
): RouteRepository {
    override fun findByStationId(departureStationId: String, arrivalStationId: String): Route {
        val url = "${catalogUrl}/api/v1/route/search"

        val uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("departureStationId", departureStationId)
            .queryParam("arrivalStationId", arrivalStationId)

        val result = externalApiClient.callGet(uriComponentsBuilder)

        val response = jacksonObjectMapper().readValue(result, RouteSearchResponse::class.java)

        return Route(
            BusinessKilometer(response.distance)
        )
    }
}