package jrpricing.fare.infra.repository.catalog

import com.fasterxml.jackson.annotation.JsonProperty

data class CatalogFindBasicFareResponse(
    @JsonProperty("routeId")
    val routeId: String,
    @JsonProperty("amount")
    val amount: Int
)
