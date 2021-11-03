package domain.model.discount.largegroup

import domain.model.shared.Passengers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DiscountPassengerCountTest {

    @Test
    fun `大人50人の場合は大人の割引人数が1人であること`() {
        val passengers = Passengers(50, 0)

        val discountPassengerCount = DiscountPassengerCount(passengers)

        Assertions.assertEquals(1, discountPassengerCount.adultDiscountNumber())
    }

    @Test
    fun `大人100人の場合は大人の割引人数が2人であること`() {
        val passengers = Passengers(100, 0)

        val discountPassengerCount = DiscountPassengerCount(passengers)

        Assertions.assertEquals(2, discountPassengerCount.adultDiscountNumber())
    }

    @Test
    fun `大人1人・子供が49人の場合は、大人の割引人数が1人であること`() {
        val passengers = Passengers(1, 49)

        val discountPassengerCount = DiscountPassengerCount(passengers)

        Assertions.assertEquals(1, discountPassengerCount.adultDiscountNumber())
        Assertions.assertEquals(0, discountPassengerCount.childDiscountNumber())
    }

    @Test
    fun `大人1人・子供が99人の場合は、大人の割引人数が1人、子供の割引人数が1人であること`() {
        val passengers = Passengers(1, 99)

        val discountPassengerCount = DiscountPassengerCount(passengers)

        Assertions.assertEquals(1, discountPassengerCount.adultDiscountNumber())
        Assertions.assertEquals(1, discountPassengerCount.childDiscountNumber())
    }
}