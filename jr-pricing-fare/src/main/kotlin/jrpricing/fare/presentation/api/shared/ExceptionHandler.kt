package jrpricing.fare.presentation.api.shared

import jrpricing.fare.domain.exception.DomainException
import jrpricing.fare.infra.external.exception.CatalogApiClientException
import jrpricing.fare.usecase.exception.AssertionFailException
import jrpricing.fare.usecase.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(DomainException::class)
    fun handleDomainException(
        e: DomainException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(e.message!!, e.errorCode.value),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(AssertionFailException::class)
    fun handleAssertionFailException(
        e: AssertionFailException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(e.message!!, e.errorCode.value)

        when (e.errorCode) {
            ErrorCode.NOTFOUND_ASSERTION -> {
                return ResponseEntity(
                    errorResponse,
                    HttpStatus.NOT_FOUND
                )
            }
            ErrorCode.UNAUTHORIZED_ASSERTION -> {
                return ResponseEntity(
                    errorResponse,
                    HttpStatus.UNAUTHORIZED
                )
            }
            else -> {
                return ResponseEntity(
                    errorResponse,
                    HttpStatus.BAD_REQUEST
                )
            }
        }
    }

    @ExceptionHandler(CatalogApiClientException::class)
    fun handleExternalApiError(e: CatalogApiClientException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(e.message!!, 500),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleInternalServerError(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(e.message!!, 999),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}