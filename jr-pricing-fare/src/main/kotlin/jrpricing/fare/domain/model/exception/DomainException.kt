package jrpricing.fare.domain.model.exception

/**
 * ドメイン層の例外
 */
class DomainException(
    message: String,
    val errorCode: ErrorCode = ErrorCode.UNKNOWN,
): RuntimeException(message) {
}