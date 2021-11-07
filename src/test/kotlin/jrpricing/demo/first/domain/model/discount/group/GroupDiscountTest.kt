package jrpricing.demo.first.domain.model.discount.group

import jrpricing.demo.first.domain.model.discount.DiscountRate
import jrpricing.demo.first.domain.model.fare.Fare
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.ticket.DepartureDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate


class GroupDiscountTest {
    @Nested
    inner class AfterDiscounted() {
        @Test
        fun `割引率が10%の割引金額が正しいこと`() {
            val fare = Fare(Price(10000))
            val discountRate = DiscountRate.RATE_10

            val discount = GroupDiscount(fare, discountRate)

            val result = discount.afterDiscounted()

            Assertions.assertEquals(Price(9000), result)
        }

        @Test
        fun `割引率が15%の割引金額が正しいこと`() {
            val fare = Fare(Price(10050))
            val discountRate = DiscountRate.RATE_15

            val discount = GroupDiscount(fare, discountRate)

            val result = discount.afterDiscounted()

            Assertions.assertEquals(Price(8540), result)
        }
    }
}