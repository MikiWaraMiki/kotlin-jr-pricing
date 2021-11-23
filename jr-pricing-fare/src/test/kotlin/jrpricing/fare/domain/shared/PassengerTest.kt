package jrpricing.fare.domain.shared

import jrpricing.fare.domain.exception.DomainException
import jrpricing.fare.domain.shared.Passenger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PassengerTest {
    @Test
    fun `乗車人数が1人未満の場合はエラーが発生すること`() {
        val target: () -> Unit = {
            Passenger(0, 0)
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("乗車人数は1人以上である必要があります", exception.message)
    }

    @Test
    fun `乗車人数が1人以上の場合は、エラーが発生しないこと`() {
        val passenger = Passenger(1, 1)

        Assertions.assertEquals(1, passenger.adultCount)
        Assertions.assertEquals(1, passenger.childCount)
        Assertions.assertEquals(2, passenger.total)
    }
}