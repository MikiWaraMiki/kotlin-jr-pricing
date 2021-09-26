package domain.model.train

/**
 * 席区分
 */
enum class SeatType(private val label: String) {
    NON_RESERVED("non_reserved"),
    RESERVED("reserved");

    companion object {
        fun fromLabel(label: String): SeatType {
            return values().firstOrNull() { it.label == label } ?:
                throw IllegalArgumentException("指定席もしくは自由席を選択してください")
        }
    }
}