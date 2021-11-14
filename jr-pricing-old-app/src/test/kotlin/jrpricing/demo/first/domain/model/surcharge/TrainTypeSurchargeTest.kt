package jrpricing.demo.first.domain.model.surcharge

import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.station.Station
import jrpricing.demo.first.domain.model.train.TrainType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TrainTypeSurchargeTest {

    @Nested
    inner class CalcTest() {
        @Test
        fun `のぞみを利用していない場合は、引数で渡した特急料金を取得できること`() {
            val surcharge = Surcharge(Price(2000))
            val route = Route(Station.TOKYO, Station.SHIN_OSAKA)

            val result = TrainTypeSurcharge.calc(surcharge, route, TrainType.HIKARI)

            Assertions.assertEquals(surcharge.price(false), result.price(false))
        }

        @Test
        fun `のぞみを利用している場合は、経路に対応する加算金額が適用された特急料金を取得できること`() {
            val surcharge = Surcharge(Price(2000))
            val route = Route(Station.TOKYO, Station.SHIN_OSAKA)

            val result = TrainTypeSurcharge.calc(surcharge, route, TrainType.NOZOMI)

            val expected = Price(2000 + 320)

            Assertions.assertEquals(expected, result.price(false))
        }
    }
}