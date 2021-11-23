package jrpricing.discount.domain.shared

import jrpricing.discount.domain.shared.TripType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TripTypeTest {
    @Test
    fun `移動手段名が片道の場合、値を取得できること`() {
        val tripType = TripType.of("half_way")

        Assertions.assertEquals(TripType.HALF_WAY, tripType)
        Assertions.assertFalse(tripType.isRoundTrip())
    }

    @Test
    fun `移動手段名が往復の場合、値を取得できること`() {
        val tripType = TripType.of("round_trip")

        Assertions.assertEquals(TripType.ROUND_TRIP, tripType)
        Assertions.assertTrue(tripType.isRoundTrip())
    }
}