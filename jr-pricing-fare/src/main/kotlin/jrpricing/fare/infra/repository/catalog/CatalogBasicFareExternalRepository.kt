package jrpricing.fare.infra.repository.catalog

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jrpricing.fare.domain.catalog.CatalogBasicFareRepository
import jrpricing.fare.domain.fare.BasicFare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.TripRoute
import jrpricing.fare.infra.external.ExternalApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.util.UriComponentsBuilder

/**
 * 運賃カタログ取得実装クラス
 */
@Repository
class CatalogBasicFareExternalRepository(
    private val externalApiClient: ExternalApiClient,
    @Value("\${jr-fare-app-conf.catalogBaseUrl") private val catalogUrl: String
): CatalogBasicFareRepository {

    override fun findBasicFare(tripRoute: TripRoute): BasicFare {
        val uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(catalogUrl)
            .queryParam("arrivalStationId", tripRoute.arrivalStationId)
            .queryParam("departureStationId", tripRoute.departureStationId)

        val result = externalApiClient.callGet(uriComponentsBuilder)

        val response = jacksonObjectMapper().readValue(result, CatalogFindBasicFareResponse::class.java)

        return BasicFare.of(Amount.of(response.amount))
    }
}