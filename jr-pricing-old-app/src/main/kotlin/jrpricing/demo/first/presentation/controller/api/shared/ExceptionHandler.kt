package jrpricing.demo.first.presentation.controller.api.shared

import jrpricing.demo.first.domain.model.exception.DomainException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(DomainException::class)
    fun handleDomainException(
        e: DomainException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(e.errorMessage, e.errorCode.code),
            HttpStatus.BAD_REQUEST
        )
    }
}