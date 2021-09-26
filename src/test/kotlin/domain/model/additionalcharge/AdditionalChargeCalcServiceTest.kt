package domain.model.additionalcharge

import domain.model.shared.Price
import domain.model.shared.Route
import domain.model.station.Station
import domain.model.train.TrainType
import org.junit.jupiter.api.*

class AdditionalChargeCalcServiceTest {
    private val service = AdditionalChargeCalcService()

    @Nested
    @DisplayName("isRequiredAdditionalChargeメソッドのテスト")
    inner class IsRequiredAdditionalChargeTest() {
        @Test
        fun `乗車する列車区分がのぞみの場合はtrueを返す`() {
            Assertions.assertTrue(service.isRequiredAdditionalCharge(TrainType.NOZOMI))
        }

        @Test
        fun `乗車する列車区分がのぞみでない場合はfalseを返す`() {
            Assertions.assertFalse(service.isRequiredAdditionalCharge(TrainType.HIKARI))
        }
    }

    @Nested
    @DisplayName("calcAdditionalChargeメソッドのテスト")
    inner class CalcAdditionalChargeTest() {
        @Test
        fun `指定した経路の追加料金を取得できること`() {
            val price = service.calcAdditionalCharge(
                Route(Station.TOKYO, Station.SHIN_OSAKA)
            )

            Assertions.assertEquals(Price(320), price)
        }
    }
}