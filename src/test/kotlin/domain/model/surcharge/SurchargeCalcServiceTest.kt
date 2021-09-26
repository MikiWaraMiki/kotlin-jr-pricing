package domain.model.surcharge

import domain.model.shared.Price
import domain.model.shared.Route
import domain.model.station.Station
import domain.model.train.SeatType
import domain.model.train.TrainType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SurchargeCalcServiceTest {
    private val surchargeCalcService = SurchargeCalcService()
    private val TOKYO_SHINOSAKA_SURCHAGE_PRICE = 5490
    private val TOKYO_SHINOSAKA_NOZOMI_ADDTIONAL_PRICE = 320

    @Test
    fun `指定席・ひかり利用時の特急料金の結果が正しいこと`() {
        val result = surchargeCalcService.calcPrice(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            TrainType.HIKARI,
            SeatType.RESERVED
        )

        val expectedPrice = Price(TOKYO_SHINOSAKA_SURCHAGE_PRICE) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `自由席利用の場合は530円値引きされた特急料金であること`() {
        val result = surchargeCalcService.calcPrice(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            TrainType.HIKARI,
            SeatType.NON_RESERVED
        )

        val expectedPrice = Price(TOKYO_SHINOSAKA_SURCHAGE_PRICE - 530) // 東京->新大阪

        Assertions.assertEquals(expectedPrice, result)
    }

    @Test
    fun `のぞみ利用時の場合は、割増された特急料金であること`() {
        val result = surchargeCalcService.calcPrice(
            Route(Station.TOKYO, Station.SHIN_OSAKA),
            TrainType.NOZOMI,
            SeatType.RESERVED
        )

        val expectedPrice = Price(
            TOKYO_SHINOSAKA_SURCHAGE_PRICE + TOKYO_SHINOSAKA_NOZOMI_ADDTIONAL_PRICE
        )

        Assertions.assertEquals(expectedPrice, result)
    }
}