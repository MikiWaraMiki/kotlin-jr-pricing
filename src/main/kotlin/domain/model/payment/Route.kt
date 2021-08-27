package domain.model.payment

/**
 * 経路
 */
class Route(
    val departureStation: DepartureStation,
    val arrivalStation: ArrivalStation
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Route

        if (departureStation != other.departureStation) return false
        if (arrivalStation != other.arrivalStation) return false

        return true
    }

    override fun hashCode(): Int {
        return 31 * departureStation.hashCode() + arrivalStation.hashCode()
    }
}