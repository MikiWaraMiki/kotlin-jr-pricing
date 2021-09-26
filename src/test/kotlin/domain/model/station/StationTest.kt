package domain.model.station

import org.junit.jupiter.api.*

class StationTest {

    @Nested
    @DisplayName("fromLabelメソッドのテスト")
    inner class FromLabelTest {
        @Test
        fun `存在する駅の場合は、Stationオブジェクトが返却される`() {
            val station = Station.fromLabel("tokyo")

            Assertions.assertEquals(Station.TOKYO, station)
        }

        @Test
        fun `存在しない駅の場合は、例外が発生すること`() {
            val error = assertThrows<IllegalArgumentException> {
                Station.fromLabel("miyagi")
            }

            Assertions.assertEquals("存在しない駅です", error.message)
        }
    }
}