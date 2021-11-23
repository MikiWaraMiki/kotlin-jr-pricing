package jrpricing.discount.domain.route

class Route(
    private val businessKilometer: BusinessKilometer
) {
    fun kilometer(): Int {
        return businessKilometer.value
    }
}