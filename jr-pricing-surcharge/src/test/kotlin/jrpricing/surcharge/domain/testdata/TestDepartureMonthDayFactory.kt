package jrpricing.surcharge.domain.testdata

import jrpricing.surcharge.domain.shared.DepartureMonthDay
import java.time.LocalDate

object TestDepartureMonthDayFactory {
    fun create(localDate: LocalDate = LocalDate.now()): DepartureMonthDay{
        return DepartureMonthDay.fromLocalDate(localDate)
    }
    fun createPeakDepartureMonthDay(): DepartureMonthDay {
        val peakLocalDate = LocalDate.of(LocalDate.now().year, 12, 25)
        return create(localDate = peakLocalDate)
    }

    fun createOffPeakDepartureMonthDay(): DepartureMonthDay {
        val offPeakLocalDate = LocalDate.of(LocalDate.now().year, 1, 16)
        return create(localDate = offPeakLocalDate)
    }

    fun createRegularDepartureMonthDay(): DepartureMonthDay {
        val regularLocalDate = LocalDate.of(LocalDate.now().year, 12, 24)
        return create(localDate = regularLocalDate)
    }
}