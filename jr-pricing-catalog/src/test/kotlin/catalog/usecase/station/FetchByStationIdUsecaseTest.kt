package catalog.usecase.station

import io.mockk.every
import io.mockk.mockk
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.repository.station.StationRepository
import catalog.testdata.station.TestStationFactory
import jrpricing.catalog.usecase.exception.AssertionFailException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FetchByStationIdUsecaseTest {
    private val stationRepository: StationRepository = mockk()

    private val usecase = FetchByStationIdUsecase(stationRepository)

    @Test
    fun `IDで指定した駅が存在する場合はIDに該当するStationエンティティを返すこと`() {
        val station = TestStationFactory.create()
        every { stationRepository.findById(station.stationId) }.returns(station)

        val result = usecase.execute(station.stationId)

        Assertions.assertEquals(station.stationId, result.stationId)
        Assertions.assertEquals(station.name(), result.name())
    }

    @Test
    fun `IDで指定した駅が存在しない場合はAssertionFailExceptionが発生すること`() {
        val stationId = StationId.create()
        every { stationRepository.findById(stationId) }.returns(null)

        val target: () -> Unit = {
            usecase.execute(stationId)
        }

        val exception = assertThrows<AssertionFailException>(target)

        Assertions.assertEquals("指定した駅は存在しません", exception.message)
    }
}