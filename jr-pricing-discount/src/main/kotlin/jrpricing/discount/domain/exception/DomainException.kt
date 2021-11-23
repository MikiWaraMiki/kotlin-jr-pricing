package jrpricing.discount.domain.exception

/**
 * ドメイン層の例外
 */
class DomainException(
    message: String,
    val errorCode: ErrorCode = ErrorCode.UNKNOWN,
): RuntimeException(message) {
}