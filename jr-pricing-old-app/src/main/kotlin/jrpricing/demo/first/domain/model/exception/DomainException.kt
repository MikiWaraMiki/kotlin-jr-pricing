package jrpricing.demo.first.domain.model.exception

/**
 * ドメイン層の例外
 */
class DomainException(
    val errorMessage: String,
    val errorCode: ErrorCode = ErrorCode.UNKNOWN,
): RuntimeException(errorMessage) {
}