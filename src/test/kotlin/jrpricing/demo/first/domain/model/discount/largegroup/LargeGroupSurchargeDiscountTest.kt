package jrpricing.demo.first.domain.model.discount.largegroup

import jrpricing.demo.first.domain.model.fare.Fare
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.surcharge.Surcharge
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LargeGroupSurchargeDiscountTest {
    @Test
    fun `大人50人の場合は、大人1人分の運賃と特急料金の合計が割引金額になること`() {
        val surcharge = Surcharge(Price(1000))
        val passengers = Passengers(50, 0)

        val largeGroupSurchargeDiscount = LargeGroupSurchargeDiscount(surcharge, passengers)

        val expected = Price(surcharge.price(false).value * 49)
        val result = largeGroupSurchargeDiscount.afterDiscounted()

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `大人100人の場合は、大人2人分の運賃と特急料金の合計が割引金額になること`() {
        val surcharge = Surcharge(Price(1000))
        val passengers = Passengers(100, 0)

        val largeGroupSurchargeDiscount = LargeGroupSurchargeDiscount(surcharge, passengers)

        val expected = Price(surcharge.price(false).value * 98)
        val result = largeGroupSurchargeDiscount.afterDiscounted()

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `子供50人の場合は、子供1人分の運賃と特急料金の合計が割引金額になること`() {
        val surcharge = Surcharge(Price(1000))
        val passengers = Passengers(0, 50)

        val largeGroupSurchargeDiscount = LargeGroupSurchargeDiscount(surcharge, passengers)

        val expected = Price(surcharge.price(true).value * 49)
        val result = largeGroupSurchargeDiscount.afterDiscounted()

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `子供100人の場合は、子供2人分の運賃と特急料金の合計が割引金額になること`() {
        val surcharge = Surcharge(Price(1000))
        val passengers = Passengers(0, 100)

        val largeGroupSurchargeDiscount = LargeGroupSurchargeDiscount(surcharge, passengers)

        val expected = Price(surcharge.price(true).value * 98)
        val result = largeGroupSurchargeDiscount.afterDiscounted()

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `大人1人・子供99人の場合は、大人1人分の運賃と特急料金+子供1人分の運賃と特急料金の合計金額が割引金額になること`() {
        val surcharge = Surcharge(Price(1000))
        val passengers = Passengers(1, 99)

        val largeGroupSurchargeDiscount = LargeGroupSurchargeDiscount(surcharge, passengers)

        val expected = Price(
            surcharge.price(false).value * 0 + surcharge.price(true).value * 98
        )
        val result = largeGroupSurchargeDiscount.afterDiscounted()

        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `大人99人・子供1人の場合は、大人2人分の運賃特急料金が割引されていること`() {
        val surcharge = Surcharge(Price(1000))
        val passengers = Passengers(99, 1)

        val largeGroupSurchargeDiscount = LargeGroupSurchargeDiscount(surcharge, passengers)

        val expected = Price(
            surcharge.price(false).value * 97 + surcharge.price(true).value * 1
        )
        val result = largeGroupSurchargeDiscount.afterDiscounted()

        Assertions.assertEquals(expected, result)
    }
}