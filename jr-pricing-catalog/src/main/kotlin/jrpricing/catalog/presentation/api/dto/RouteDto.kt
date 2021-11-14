package jrpricing.catalog.presentation.api.dto

data class RouteDto(
    val id: String,
    val distance: Int,
    val departureStation: StationDto,
    val arrivalStation: StationDto
)