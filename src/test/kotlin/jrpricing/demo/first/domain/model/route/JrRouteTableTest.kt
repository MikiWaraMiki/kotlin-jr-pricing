package jrpricing.demo.first.domain.model.route

import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.station.Station
import org.junit.jupiter.api.*

class JrRouteTableTest {
    private val jrRouteTable = JrRouteTable()

    @Nested
    @DisplayName("ofメソッドのテスト")
    inner class OfTest {
        @Test
        fun `経路表に存在しない出発駅を指定した場合はエラーが出る`() {
            val target: () -> Unit = {
                JrRouteTable.of(Station.SHIN_OSAKA, Station.HIMEJI)
            }

            val exception = assertThrows<DomainException>(target)

            Assertions.assertEquals("経路表に登録されていない出発駅です", exception.message)
        }

        @Test
        fun `経路表に存在しない到着駅を指定した場合はエラーが出る`() {
            val target: () -> Unit = {
                JrRouteTable.of(Station.TOKYO, Station.TOKYO)
            }

            val exception = assertThrows<DomainException>(target)


            Assertions.assertEquals("経路表に登録されていない到着駅です", exception.message)
        }

        @Test
        fun `経路表に存在する出発駅と到着駅の場合は経路を取得できる`() {
            val route = JrRouteTable.of(Station.TOKYO, Station.SHIN_OSAKA)

            Assertions.assertEquals(Route(Station.TOKYO, Station.SHIN_OSAKA), route)
        }

    }
}