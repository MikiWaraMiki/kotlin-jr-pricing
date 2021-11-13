package jrpricing.catalog.usecase.exception

/**
 * ユースケース事前条件例外
 */
class AssertionFailException(
    message: String
): RuntimeException(message) {
}