package jrpricing.discount.usecase.exception

enum class ErrorCode(val value: Int) {
    NOTFOUND_ASSERTION(404),
    UNAUTHORIZED_ASSERTION(401)
}