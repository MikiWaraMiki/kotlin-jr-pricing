package jrpricing.catalog.presentation.api.shared

data class ErrorResponse(
    val message: String,
    val errorCode: Int
)
