package jrpricing.fare.domain.model.exception

enum class ErrorCode(val value: Int) {
    INVALID_INPUT(400),
    MAYBE_PROGRAM_MISS(600),
    UNKNOWN(999);
}