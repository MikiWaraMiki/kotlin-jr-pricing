package jrpricing.catalog.domain.exception

import jrpricing.demo.first.domain.model.exception.ErrorCode

/**
 * ドメイン層の例外
 */
class DomainException(
    val errorMessage: String,
    val errorCode: ErrorCode = ErrorCode.UNKNOWN,
): RuntimeException(errorMessage) {
}