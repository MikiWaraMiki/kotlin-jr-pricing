package domain.model.season

import java.time.LocalDate
import java.time.MonthDay

/**
 * 季節区分
 */
enum class SeasonType(
    private val label: String,
    private val start: String,
    private val end: String,
    private val season: Season = Season.of(start, end)
) {
    PEAK("繁忙期", "12-25", "01-10"),
    OFF_PEAK("閑散期", "01-16", "01-30"),
    // TODO: 通常期の指定方法が仕様を表現しているか再検討する
    REGULAR("通常期", "01-01", "12-31");

    companion object {
        fun of(date: LocalDate): SeasonType {
            val monthDay = MonthDay.from(date)
            val seasonType = values().firstOrNull {
                it.season.contains(monthDay)
            } ?: throw IllegalStateException("季節区分に存在しない日付です")

            return seasonType
        }
    }
}