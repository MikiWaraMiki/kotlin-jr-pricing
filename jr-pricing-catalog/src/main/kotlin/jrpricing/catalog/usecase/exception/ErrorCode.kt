package jrpricing.catalog.usecase.exception

enum class ErrorCode(val value: Int) {
    NOTFOUND_ASSERTION(404),
    UNAUTHORIZED_ASSERTION(401),
    MASTER_DATA_NOTFOUND_ASSERTION(500)
}