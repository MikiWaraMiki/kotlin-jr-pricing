package jrpricing.catalog.presentation.api.shared

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

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
}