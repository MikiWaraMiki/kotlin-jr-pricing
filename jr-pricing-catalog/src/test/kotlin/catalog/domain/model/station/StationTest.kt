package catalog.domain.model.station

import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.model.station.StationName
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class StationTest {

    @Nested
    inner class ReConstructorTest() {
        @Test
        fun `引数で渡した駅IDと駅名でエンティティが作成されていること`() {
            val stationId = StationId.create()
            val stationName = StationName("東京駅")

            val station = Station.reConstructor(stationId, stationName)

            Assertions.assertEquals(stationId, station.stationId)
            Assertions.assertEquals(stationName.value, station.name())
        }
    }
}