package domain.model.shared

/**
 * 搭乗員数クラス
 */
class Passengers(
    adultPassengerCount: Int,
    childPassengerCount: Int
) {
    val adults: Int
    val childs: Int

    init {
        val totalPassengers = adultPassengerCount + childPassengerCount

        if (totalPassengers < 1) throw IllegalArgumentException("乗車人数が０人です。")

        adults = adultPassengerCount
        childs = childPassengerCount
    }

    fun totalPassengers(): Int {
        return adults + childs
    }
}