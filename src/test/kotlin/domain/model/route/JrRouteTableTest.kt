package domain.model.route

import domain.model.station.Station
import org.junit.jupiter.api.*

class JrRouteTableTest {
    private val jrRouteTable = JrRouteTable()

    @Nested
    @DisplayName("ofメソッドのテスト")
    inner class OfTest {
        @Test
        fun `経路表に存在しない出発駅を指定した場合はエラーが出る`() {
            val error = assertThrows<IllegalArgumentException> {
                JrRouteTable.of(Station.SHIN_OSAKA, Station.HIMEJI)
            }

            Assertions.assertEquals("経路表に登録されていない出発駅です", error.message)
        }

        @Test
        fun `経路表に存在しない到着駅を指定した場合はエラーが出る`() {
            val error = assertThrows<IllegalArgumentException> {
                JrRouteTable.of(Station.TOKYO, Station.TOKYO)
            }

            Assertions.assertEquals("経路表に登録されていない到着駅です", error.message)
        }

        @Test
        fun `経路表に存在する出発駅と到着駅の場合は経路を取得できる`() {
            val route = JrRouteTable.of(Station.TOKYO, Station.SHIN_OSAKA)

            Assertions.assertEquals(Route(Station.TOKYO, Station.SHIN_OSAKA), route)
        }

    }
}