package jrpricing.demo.first.domain.model.surcharge

import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.station.Station
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class SurchargeTableTest {
    private val surchargeTable = SurchargeTable()

    @Test
    fun `東京から新大阪の特急料金が5490円であること`() {
        val price = surchargeTable.price(
            Route(Station.TOKYO, Station.SHIN_OSAKA)
        )

        Assertions.assertEquals(Price(5490), price)
    }

    @Test
    fun `東京から姫路の特急料金が5920円であること`() {
        val price = surchargeTable.price(
            Route(Station.TOKYO, Station.HIMEJI)
        )

        Assertions.assertEquals(Price(5920), price)
    }

    @Test
    fun `料金表に存在しない経路の場合はエラーが発生すること`() {
        val error = assertThrows<IllegalArgumentException> {
            surchargeTable.price(
                Route(Station.HIMEJI, Station.SHIN_OSAKA)
            )
        }

        Assertions.assertEquals("料金表に存在しません", error.message)
    }
}