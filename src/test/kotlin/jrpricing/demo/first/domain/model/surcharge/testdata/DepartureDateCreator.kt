package jrpricing.demo.first.domain.model.surcharge.testdata

import jrpricing.demo.first.domain.model.ticket.DepartureDate
import java.time.LocalDate

object DepartureDateCreator {
    val REGULAR_DEPATURE_DATE =  DepartureDate(
        LocalDate.of(LocalDate.now().year + 1, 5, 21)
    )
    val PEAK_DEPARTURE_DATE = DepartureDate(
        LocalDate.of(LocalDate.now().year + 1, 12,31)
    )
    val OFF_PEAK_DEPARTURE_DATE = DepartureDate(
        LocalDate.of(LocalDate.now().year + 1, 1,16)
    )

}