package jrpricing.catalog.presentation.api.basicsurcharge

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.mockk.every
import io.mockk.mockk
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.domain.model.train.TrainType
import jrpricing.catalog.presentation.api.shared.ExceptionHandler
import jrpricing.catalog.testdata.route.TestRouteFactory
import jrpricing.catalog.testdata.station.TestStationFactory
import jrpricing.catalog.testdata.surcharge.TestBasicSurchargeFactory
import jrpricing.catalog.usecase.basicsurcharge.FindBasicSurchargeUsecase
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.lang.Exception
import java.nio.charset.StandardCharsets

class BasicSurchargeControllerTest {

    @Nested
    inner class SearchTest() {
        private val findBasicSurchargeUsecase: FindBasicSurchargeUsecase = mockk()
        private val basicSurchargeController = BasicSurchargeController(findBasicSurchargeUsecase)

        @Test
        fun `出発駅と到着駅の経路と特急料金が存在する場合は200レスポンスを返し、レスポンスのスキーマが正しいこと`() {
            val departureStation = TestStationFactory.create()
            val arrivalStation = TestStationFactory.create()
            val route = TestRouteFactory.create(
                departureStationId = departureStation.stationId,
                arrivalStationId = arrivalStation.stationId
            )
            val basicSurcharge = TestBasicSurchargeFactory.create(
                routeId = route.routeId,
                amount = Amount(10000)
            )

            every { findBasicSurchargeUsecase.execute(departureStation.stationId, arrivalStation.stationId, TrainType.HIKARI) }.returns(basicSurcharge)

            val expectedResponse = SearchSurchargeResponse(
                routeId = route.routeId.value,
                amount = basicSurcharge.amount.value
            )
            val expected = ObjectMapper()
                .registerKotlinModule()
                .writeValueAsString(expectedResponse)

            val mockMvc = MockMvcBuilders.standaloneSetup(basicSurchargeController).build()

            val resultResponse = mockMvc.perform(
                get("/api/v1/catalog/surcharge/search")
                    .param("departureStationId", departureStation.stationId.value)
                    .param("arrivalStationId", arrivalStation.stationId.value)
                    .param("trainTypeId", TrainType.HIKARI.id.toString())
            )
                .andExpect(status().isOk)
                .andReturn()
                .response

            val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)
            Assertions.assertEquals(expected, result)
        }

        @Test
        fun `経路が存在しない場合は404エラーを返すこと`() {
            val departureStation = TestStationFactory.create()
            val arrivalStation = TestStationFactory.create()

            every { findBasicSurchargeUsecase.execute(departureStation.stationId, arrivalStation.stationId, TrainType.HIKARI) }.throws(
                AssertionFailException("指定した駅間の経路は存在しません", ErrorCode.NOTFOUND_ASSERTION)
            )

            val mockMvc = MockMvcBuilders.standaloneSetup(basicSurchargeController)
                .setControllerAdvice(ExceptionHandler())
                .build()

            mockMvc.perform(
                get("/api/v1/catalog/fare/search")
                    .param("departureStationId", departureStation.stationId.value)
                    .param("arrivalStationId", arrivalStation.stationId.value)
                    .param("trainTypeId", TrainType.HIKARI.id.toString())
            ).andExpect(status().isNotFound)
        }

        @Test
        fun `経路は存在するが運賃が存在しない場合は500エラーを返すこと`() {
            val departureStation = TestStationFactory.create()
            val arrivalStation = TestStationFactory.create()

            every { findBasicSurchargeUsecase.execute(departureStation.stationId, arrivalStation.stationId, TrainType.HIKARI) }.throws(
                AssertionFailException("指定した経路の運賃は存在しません", ErrorCode.MASTER_DATA_NOTFOUND_ASSERTION)
            )

            val mockMvc = MockMvcBuilders.standaloneSetup(basicSurchargeController)
                .setControllerAdvice(ExceptionHandler())
                .build()

            mockMvc.perform(
                get("/api/v1/catalog/surcharge/search")
                    .param("departureStationId", departureStation.stationId.value)
                    .param("arrivalStationId", arrivalStation.stationId.value)
                    .param("trainTypeId", TrainType.HIKARI.id.toString())
            ).andExpect(status().isInternalServerError)
        }
    }
}