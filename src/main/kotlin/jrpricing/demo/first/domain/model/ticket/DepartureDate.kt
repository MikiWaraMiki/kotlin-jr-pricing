package jrpricing.demo.first.domain.model.ticket

import java.time.LocalDate

/**
 * 乗車日クラス
 */
class DepartureDate(aDate: LocalDate) {
    val date: LocalDate

    init {
        val currentDate = LocalDate.now()

        val diff = currentDate.compareTo(aDate)

        if (diff > 0) throw IllegalArgumentException("本日より前の日付は指定できません")

        date = aDate
    }

    companion object {
        fun of(dateString: String): DepartureDate {
            val localDate = try {
                LocalDate.parse(dateString)
            } catch(e: Exception) {
                throw IllegalArgumentException("日付以外の値を指定しています")
            }

            return DepartureDate(localDate)
        }
    }
}