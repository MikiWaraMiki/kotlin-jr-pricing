package jrpricing.discount.domain.route

class Route(
    private val distanceKilometer: DistanceKilometer
) {
    fun kilometer(): Int {
        return distanceKilometer.value
    }
}