package domain.model.surcharge.additionalcharge

import domain.model.shared.Price
import domain.model.route.Route
import domain.model.station.Station
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class NozomiAdditionalChargeTableTest {
    private val nozomiAdditionalChargeTable = NozomiAdditionalChargeTable()

    @Test
    fun `東京から新大阪までの追加料金が320円であること`() {
        val price = nozomiAdditionalChargeTable.price(
            Route(Station.TOKYO, Station.SHIN_OSAKA)
        )

        Assertions.assertEquals(Price(320), price)
    }

    @Test
    fun `東京から姫路までの追加料金が530円であること`() {
        val price = nozomiAdditionalChargeTable.price(
            Route(Station.TOKYO, Station.HIMEJI)
        )

        Assertions.assertEquals(Price(530), price)
    }

    @Test
    fun `料金表に存在しない場合は例外が発生すること`() {
        val error = assertThrows<IllegalArgumentException> {
            nozomiAdditionalChargeTable.price(
                Route(Station.HIMEJI, Station.TOKYO)
            )
        }

        Assertions.assertEquals("料金表に存在しません", error.message)
    }
}