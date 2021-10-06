package domain.model.ticket

/**
 * チケット区分クラス
 */
enum class TicketType(private val label: String, private val typeName: String) {
    ONE_WAY("片道", "one_way"),
    ROUND_TRIP("往復", "round_trip");

    companion object {
        fun of(typeName: String): TicketType {
            return values().firstOrNull() { it.typeName == typeName }
                ?: throw IllegalArgumentException("片道もしくは往復を選択してください")
        }
    }
}