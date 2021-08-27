package domain.model.payment

import domain.model.station.Station
import org.junit.jupiter.api.*
import java.lang.IllegalArgumentException

class DepartureStationTest {

    @Test
    @DisplayName("新大阪を出発駅にした場合エラーが出ること")
    fun notSelectAbleOsaka() {
        val error = assertThrows<IllegalArgumentException> {
            DepartureStation(Station.SHIN_OSAKA)
        }

        Assertions.assertEquals("出発駅は東京のみ選択できます", error.message)
    }

    @Test
    @DisplayName("姫路を出発駅にした場合エラーが出ること")
    fun notSelectAbleHimeji() {
        val error = assertThrows<IllegalArgumentException> {
            DepartureStation(Station.HIMEJI)
        }

        Assertions.assertEquals("出発駅は東京のみ選択できます", error.message)

    }

    @Test
    @DisplayName("東京を出発駅に指定できること")
    fun selectAbleTokyo() {
        assertDoesNotThrow {
            DepartureStation(Station.TOKYO)
        }
    }
}