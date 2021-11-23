package jrpricing.catalog.usecase.basicsurcharge

import io.mockk.every
import io.mockk.mockk
import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.model.surcharge.BasicSurchargeRepository
import jrpricing.catalog.domain.model.surcharge.NozomiAdditionalChargeRepository
import jrpricing.catalog.domain.model.train.TrainType
import jrpricing.catalog.testdata.route.TestRouteFactory
import jrpricing.catalog.testdata.surcharge.TestBasicSurchargeFactory
import jrpricing.catalog.testdata.surcharge.TestNozomiAdditionalChargeFactory
import jrpricing.catalog.usecase.exception.AssertionFailException
import org.junit.jupiter.api.*
import java.lang.Exception

internal class FindBasicSurchargeUsecaseTest {
    private val routeRepository: RouteRepository = mockk()
    private val basicSurchargeRepository: BasicSurchargeRepository = mockk()
    private val nozomiAdditionalChargeRepository: NozomiAdditionalChargeRepository = mockk()


    @Nested
    @DisplayName("正常系テスト")
    inner class SuccessTest() {

        @Test
        fun `ひかり利用時の料金を取得できること`() {
            val departureStationId = StationId.create()
            val arrivalStationId = StationId.create()
            val trainType = TrainType.HIKARI
            val route = TestRouteFactory.create(departureStationId = departureStationId, arrivalStationId = arrivalStationId)
            val basicSurcharge = TestBasicSurchargeFactory.create(routeId = route.routeId, amount = Amount(1000))

            every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(route)
            every{ basicSurchargeRepository.findByRouteId(route.routeId) }.returns(basicSurcharge)
            val usecase = FindBasicSurchargeUsecase(routeRepository, basicSurchargeRepository, nozomiAdditionalChargeRepository)

            val result = usecase.execute(departureStationId, arrivalStationId, trainType)

            Assertions.assertEquals(basicSurcharge.routeId, result.routeId)
            Assertions.assertEquals(basicSurcharge.amount, result.amount)
        }

        @Test
        fun `のぞみ利用時の料金を取得できること`() {
            val departureStationId = StationId.create()
            val arrivalStationId = StationId.create()
            val trainType = TrainType.NOZOMI

            val route = TestRouteFactory.create(departureStationId = departureStationId, arrivalStationId = arrivalStationId)
            val basicSurcharge = TestBasicSurchargeFactory.create(routeId = route.routeId, amount = Amount(1000))
            val nozomiAdditionalCharge = TestNozomiAdditionalChargeFactory.create(routeId = route.routeId, amount = Amount(1000))

            every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(route)
            every{ basicSurchargeRepository.findByRouteId(route.routeId) }.returns(basicSurcharge)
            every { nozomiAdditionalChargeRepository.findByRouteId(route.routeId) }.returns(nozomiAdditionalCharge)
            val usecase = FindBasicSurchargeUsecase(routeRepository, basicSurchargeRepository, nozomiAdditionalChargeRepository)

            val result = usecase.execute(departureStationId, arrivalStationId, trainType)
            val expectedAmount = Amount(basicSurcharge.amount.value + nozomiAdditionalCharge.amount.value)

            Assertions.assertEquals(basicSurcharge.routeId, result.routeId)
            Assertions.assertEquals(expectedAmount, result.amount)
        }
    }


    @Nested
    @DisplayName("事前条件の例外テスト")
    inner class AssertionFailExceptionTest() {
        @Test
        fun `指定した駅間の経路が存在しない場合はAssertionFailExceptionが発生すること`() {
            val departureStationId = StationId.create()
            val arrivalStationId = StationId.create()
            val trainType = TrainType.HIKARI

            every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(null)

            val usecase = FindBasicSurchargeUsecase(routeRepository, basicSurchargeRepository, nozomiAdditionalChargeRepository)

            val target: () -> Unit = {
                usecase.execute(departureStationId, arrivalStationId, trainType)
            }

            val exception = assertThrows<AssertionFailException>(target)

            Assertions.assertEquals("指定した駅間の経路は存在しません", exception.message)
        }
    }

    @Nested
    @DisplayName("マスタデータが登録されていない場合に発生する例外テスト")
    inner class ExceptionTest() {
        @Test
        fun `指定した経路の特急料金が登録されていない場合は、Exceptionが発生すること`() {
            val departureStationId = StationId.create()
            val arrivalStationId = StationId.create()
            val trainType = TrainType.HIKARI
            val route = TestRouteFactory.create(departureStationId = departureStationId, arrivalStationId = arrivalStationId)

            every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(route)
            every{ basicSurchargeRepository.findByRouteId(route.routeId) }.returns(null)
            val usecase = FindBasicSurchargeUsecase(routeRepository, basicSurchargeRepository, nozomiAdditionalChargeRepository)

            val target: () -> Unit = {
                usecase.execute(departureStationId, arrivalStationId, trainType)
            }

            val exception = assertThrows<AssertionFailException>(target)

            Assertions.assertEquals("指定した経路の特急料金は存在しません", exception.message)
        }

        @Test
        fun `指定した経路ののぞみ追加料金が登録されていない場合は、Exceptionが発生すること`() {
            val departureStationId = StationId.create()
            val arrivalStationId = StationId.create()
            val trainType = TrainType.NOZOMI
            val route = TestRouteFactory.create(departureStationId = departureStationId, arrivalStationId = arrivalStationId)
            val basicSurcharge = TestBasicSurchargeFactory.create(routeId = route.routeId, amount = Amount(1000))

            every { routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId) }.returns(route)
            every{ basicSurchargeRepository.findByRouteId(route.routeId) }.returns(basicSurcharge)
            every { nozomiAdditionalChargeRepository.findByRouteId(route.routeId) }.returns(null)
            val usecase = FindBasicSurchargeUsecase(routeRepository, basicSurchargeRepository, nozomiAdditionalChargeRepository)

            val target: () -> Unit = {
                usecase.execute(departureStationId, arrivalStationId, trainType)
            }

            val exception = assertThrows<AssertionFailException>(target)

            Assertions.assertEquals("指定した経路ののぞみ追加料金が存在しません", exception.message)
        }

    }

}