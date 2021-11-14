package jrpricing.demo.first.domain.model.discount.largegroup

import jrpricing.demo.first.domain.model.shared.Passengers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DiscountPassengerCountTest {

    @Test
    fun `大人31人の場合は大人の割引人数が1人であること`() {
        val passengers = Passengers(31, 0)

        val discountPassengerCount = FreePassengerCount(passengers)

        Assertions.assertEquals(1, discountPassengerCount.adultDiscountNumber())
        Assertions.assertEquals(0, discountPassengerCount.childDiscountNumber())
    }

    @Test
    fun `子供31人の場合は子供の割引人数が1人であること`() {
        val passengers = Passengers(0, 31)

        val discountPassengerCount = FreePassengerCount(passengers)

        Assertions.assertEquals(1, discountPassengerCount.childDiscountNumber())
        Assertions.assertEquals(0, discountPassengerCount.adultDiscountNumber())
    }

    @Test
    fun `大人30人・子供1人の場合は、大人の割引人数が1人であること`() {
        val passengers = Passengers(30, 1)

        val discountPassengerCount = FreePassengerCount(passengers)

        Assertions.assertEquals(1, discountPassengerCount.adultDiscountNumber())
        Assertions.assertEquals(0, discountPassengerCount.childDiscountNumber())
    }

    @Test
    fun `大人50人の場合は、大人の割引人数が1人であること`() {
        val passengers = Passengers(50, 0)

        val discountPassengerCount = FreePassengerCount(passengers)

        Assertions.assertEquals(1, discountPassengerCount.adultDiscountNumber())
        Assertions.assertEquals(0, discountPassengerCount.childDiscountNumber())
    }

    @Test
    fun `子供50人の場合は、子供の割引人数が1人であること`() {
        val passengers = Passengers(0, 50)

        val discountPassengerCount = FreePassengerCount(passengers)

        Assertions.assertEquals(0, discountPassengerCount.adultDiscountNumber())
        Assertions.assertEquals(1, discountPassengerCount.childDiscountNumber())
    }

    @Test
    fun `大人100人の場合は大人の割引人数が2人であること`() {
        val passengers = Passengers(100, 0)

        val discountPassengerCount = FreePassengerCount(passengers)

        Assertions.assertEquals(2, discountPassengerCount.adultDiscountNumber())
        Assertions.assertEquals(0, discountPassengerCount.childDiscountNumber())
    }

    @Test
    fun `子供100人の場合は子供の割引人数が2人であること`() {
        val passengers = Passengers(0, 100)

        val discountPassengerCount = FreePassengerCount(passengers)

        Assertions.assertEquals(0, discountPassengerCount.adultDiscountNumber())
        Assertions.assertEquals(2, discountPassengerCount.childDiscountNumber())
    }

    @Test
    fun `大人1人・子供が99人の場合は、大人の割引人数が1人、子供の割引人数が1人であること`() {
        val passengers = Passengers(1, 99)

        val discountPassengerCount = FreePassengerCount(passengers)

        Assertions.assertEquals(1, discountPassengerCount.adultDiscountNumber())
        Assertions.assertEquals(1, discountPassengerCount.childDiscountNumber())
    }
}