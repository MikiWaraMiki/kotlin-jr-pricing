package domain.model.additionalchage.traintype

import domain.model.additionalcharge.traintype.NozomiAdditionalChargeTable
import domain.model.shared.Price
import domain.model.shared.Route
import domain.model.station.Station
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class NozomiAdditionalChargeTableTest {
    lateinit var table: NozomiAdditionalChargeTable

    @BeforeEach
    fun setup() {
        table = NozomiAdditionalChargeTable()
    }

    @Test
    fun `東京から新大阪までの追加料金が320円であること`() {
        val price = table.price(Route(Station.TOKYO, Station.SHIN_OSAKA))

        Assertions.assertEquals(Price(320), price)
    }

    @Test
    fun `東京から姫路までの追加料金が530円であること`() {
        val price = table.price(Route(Station.TOKYO, Station.HIMEJI))

        Assertions.assertEquals(Price(530), price)
    }

    @Test
    fun `料金表に存在しない経路の場合例外が発生すること`() {
        val error = assertThrows<IllegalArgumentException> {
            table.price(Route(Station.HIMEJI, Station.TOKYO))
        }

        Assertions.assertEquals("存在しない経路です", error.message)
    }
}