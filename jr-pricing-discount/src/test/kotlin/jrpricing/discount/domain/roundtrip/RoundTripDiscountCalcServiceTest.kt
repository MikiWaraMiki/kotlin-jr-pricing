package jrpricing.discount.domain.roundtrip

import jrpricing.discount.domain.route.BusinessKilometer
import jrpricing.discount.domain.route.Route
import jrpricing.discount.domain.shared.Amount
import jrpricing.discount.domain.shared.TripType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class RoundTripDiscountCalcServiceTest {
    @Nested
    @DisplayName("往復割引計算テスト")
    inner class CalcTest() {
        @Test
        fun `往復割引の適用条件を満たさない場合は割引適用前の金額が算出されること`() {
            val beforeRoundTripDiscountedFare = BeforeRoundTripDiscountedFare(Amount.of(1000))
            val route = Route(BusinessKilometer(601))
            val tripType = TripType.HALF_WAY

            val calcService = RoundTripDiscountCalcService()

            val result = calcService.calc(beforeRoundTripDiscountedFare, route, tripType)

            val expected = Amount.of(1000)

            Assertions.assertEquals(expected, result)
        }

        @Test
        fun `往復割引の適用条件を満たす場合は、1割引された金額が算出されること`() {
            val beforeRoundTripDiscountedFare = BeforeRoundTripDiscountedFare(Amount.of(1000))
            val route = Route(BusinessKilometer(601))
            val tripType = TripType.ROUND_TRIP

            val calcService = RoundTripDiscountCalcService()

            val result = calcService.calc(beforeRoundTripDiscountedFare, route, tripType)

            val expected = Amount.of(900)

            Assertions.assertEquals(expected, result)
        }
    }
}