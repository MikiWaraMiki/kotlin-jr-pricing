package domain.model.discount.group

import domain.model.shared.Price
import domain.model.ticket.DepartureDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GroupDiscountTest {

    @Test
    fun `割引率が10%の適用期間中の割引金額が正しいこと`() {
        val basePrice = Price(10000)
        val departureDate = DepartureDate(LocalDate.of(2021, 12, 21))

        val discount = GroupDiscount.fromDepartureDate(basePrice, departureDate)

        Assertions.assertEquals(Price(9000), discount.afterDiscountedPrice())
    }

    @Test
    fun `割引率が15%の適用期間中の割引金額が正しいこと`() {
        val basePrice = Price(10050)
        val departureDate = DepartureDate(LocalDate.of(2021, 12, 20))

        val discount = GroupDiscount.fromDepartureDate(basePrice, departureDate)

        val expected = Price(8540) // 10050 * 0.85 = 8,542 -> 1円単位切り捨てで8,540
        Assertions.assertEquals(Price(8540), discount.afterDiscountedPrice())
    }
}