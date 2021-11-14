package jrpricing.demo.first.domain.model.station

import jrpricing.demo.first.domain.model.exception.DomainException
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
            val target: () -> Unit = { Station.fromLabel("miyagi") }

            val exception = assertThrows<DomainException>(target)
            Assertions.assertEquals("存在しない駅です", exception.message)
        }
    }
}