package jrpricing.catalog.usecase.exception

enum class ErrorCode(val code: Int) {
    NOTFOUND_ASSERTION(404),
    UNAUTHORIZED_ASSERTION(404)
}