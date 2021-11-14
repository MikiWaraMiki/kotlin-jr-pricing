package jrpricing.catalog.domain.exception

enum class ErrorCode(val code: Int) {
    INVALID_INPUT(400),
    MAYBE_PROGRAM_MISS(600),
    UNKNOWN(999);
}