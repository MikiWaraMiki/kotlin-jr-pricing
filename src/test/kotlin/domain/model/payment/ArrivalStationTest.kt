package domain.model.payment

import domain.model.station.Station
import org.junit.jupiter.api.*

class ArrivalStationTest {

    @Test
    @DisplayName("東京を到着駅にした場合エラーが発生すること")
    fun notSelectAbleTokyo() {
        val error = assertThrows<IllegalArgumentException> {
            ArrivalStation(Station.TOKYO)
        }

        Assertions.assertEquals(
            "到着駅は、姫路もしくは新大阪が選択可能です。",
            error.message
        )
    }

    @Test
    @DisplayName("姫路を到着駅にした場合エラーが発生しないこと")
    fun selectAble_HIMEJI() {
        assertDoesNotThrow {
            ArrivalStation(Station.HIMEJI)
        }
    }

    @Test
    @DisplayName("新大阪を到着駅にした場合エラーが発生しないこと")
    fun selectAble_SHINOSAKA() {
        assertDoesNotThrow {
            ArrivalStation(Station.SHIN_OSAKA)
        }
    }
}