package domain.model.discount.largegroup

import domain.model.fare.Fare
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.surcharge.Surcharge
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LargeGroupDiscountTest {
    @Test
    fun `大人50人の場合は、大人1人分の運賃と特急料金の合計が割引金額になること`() {
        val fare = Fare(Price(1000))
        val surcharge = Surcharge(Price(1000))

        val passengers = Passengers(50, 0)

        val largeGroupDiscount = LargeGroupDiscount(fare, surcharge, passengers)

        Assertions.assertEquals(Price(2000), largeGroupDiscount.afterDiscountedPrice())
    }

    @Test
    fun `大人100人の場合は、大人2人分の運賃と特急料金の合計が割引金額になること`() {
        val fare = Fare(Price(1000))
        val surcharge = Surcharge(Price(1000))

        val passengers = Passengers(100, 0)

        val largeGroupDiscount = LargeGroupDiscount(fare, surcharge, passengers)

        Assertions.assertEquals(Price(2000 * 2), largeGroupDiscount.afterDiscountedPrice())
    }

    @Test
    fun `子供50人の場合は、子供1人分の運賃と特急料金の合計が割引金額になること`() {
        val fare = Fare(Price(1000))
        val surcharge = Surcharge(Price(1000))

        val passengers = Passengers(0, 50)

        val largeGroupDiscount = LargeGroupDiscount(fare, surcharge, passengers)

        val expected = Price(500 + 500)
        Assertions.assertEquals(expected, largeGroupDiscount.afterDiscountedPrice())
    }

    @Test
    fun `子供100人の場合は、子供2人分の運賃と特急料金の合計が割引金額になること`() {
        val fare = Fare(Price(1000))
        val surcharge = Surcharge(Price(1000))

        val passengers = Passengers(0, 100)

        val largeGroupDiscount = LargeGroupDiscount(fare, surcharge, passengers)

        val expected = Price((500 + 500) * 2)

        Assertions.assertEquals(expected, largeGroupDiscount.afterDiscountedPrice())
    }

    @Test
    fun `大人1人・子供99人の場合は、大人1人分の運賃と特急料金+子供1人分の運賃と特急料金の合計金額が割引金額になること`() {
        val fare = Fare(Price(1000))
        val surcharge = Surcharge(Price(1000))

        val passengers = Passengers(1, 99)
        val largeGroupDiscount = LargeGroupDiscount(fare, surcharge, passengers)

        val expected = Price((1000 + 1000) + (500 + 500))

        Assertions.assertEquals(expected, largeGroupDiscount.afterDiscountedPrice())
    }
}