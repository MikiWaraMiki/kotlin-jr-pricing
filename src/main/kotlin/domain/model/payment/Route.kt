package domain.model.payment

/**
 * 経路
 */
// TODO: 出発地クラスと到着地クラスは必要ないかもしれない
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
        return departureStation.hashCode() * arrivalStation.hashCode()
    }
}