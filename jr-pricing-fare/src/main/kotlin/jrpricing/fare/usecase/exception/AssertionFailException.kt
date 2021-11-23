package jrpricing.fare.usecase.exception

/**
 * ユースケース事前条件例外
 */
class AssertionFailException(
    message: String,
    val errorCode: ErrorCode
): RuntimeException(message) {
}