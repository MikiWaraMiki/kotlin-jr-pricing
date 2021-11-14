package jrpricing.catalog.presentation.api.dto

data class RouteDto(
    val id: String,
    val departureStation: StationDto,
    val arrivalStation: StationDto
)