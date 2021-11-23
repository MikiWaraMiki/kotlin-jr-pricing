package jrpricing.fare.infra.external.exception

import org.springframework.http.HttpStatus

class CatalogApiClientException(
    message: String,
    val httpStatus: HttpStatus,
): RuntimeException(message) {
}
