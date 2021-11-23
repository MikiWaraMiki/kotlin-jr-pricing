package jrpricing.fare.infra.repository.catalog

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jrpricing.fare.domain.catalog.CatalogBasicFareRepository
import jrpricing.fare.domain.fare.BasicFare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.infra.external.CatalogApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.util.UriComponentsBuilder

/**
 * 運賃カタログ取得実装クラス
 */
@Repository
class CatalogBasicFareExternalRepository(
    private val catalogApiClient: CatalogApiClient,
    @Value("\${jr-fare-app-conf.catalogBaseUrl") private val catalogUrl: String
): CatalogBasicFareRepository {

    override fun findBasicFare(arrivalStationId: String, departureStationId: String): BasicFare {
        val uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(catalogUrl)
            .queryParam("arrivalStationId", arrivalStationId)
            .queryParam("departureStationId", departureStationId)

        val result = catalogApiClient.callGet(uriComponentsBuilder)

        val response = jacksonObjectMapper().readValue(result, CatalogFindBasicFareResponse::class.java)

        return BasicFare.of(Amount.of(response.amount))
    }
}