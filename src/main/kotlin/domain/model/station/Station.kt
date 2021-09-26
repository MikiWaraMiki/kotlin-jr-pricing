package domain.model.station

/**
 * 駅
 */
enum class Station(private val label: String) {
    TOKYO("tokyo"),
    SHIN_OSAKA("shin_osaka"),
    HIMEJI("himeji");


    companion object {
        fun fromLabel(label: String): Station {
            val station = values().firstOrNull() { it.label == label } ?:
                throw IllegalArgumentException("存在しない駅です")

            return station
        }
    }
}