package jrpricing.catalog.presentation.api.basicfare

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.mockk.every
import io.mockk.mockk
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.presentation.api.dto.RouteDto
import jrpricing.catalog.presentation.api.shared.ExceptionHandler
import jrpricing.catalog.testdata.fare.TestBasicFareFactory
import jrpricing.catalog.testdata.route.TestRouteFactory
import jrpricing.catalog.testdata.station.TestStationFactory
import jrpricing.catalog.usecase.basicfare.FindBasicFareUsecase
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.nio.charset.StandardCharsets

internal class BasicFareControllerTest {
    private val findBasicFareUsecase: FindBasicFareUsecase = mockk()
    private val basicFareController = BasicFareController(findBasicFareUsecase)

    @Nested
    inner class SearchTest() {
        @Test
        fun `出発駅と到着駅の経路と運賃が存在する場合は200レスポンスを返し、レスポンスのスキーマが正しいこと`() {
            val departureStation = TestStationFactory.create()
            val arrivalStation = TestStationFactory.create()
            val route = TestRouteFactory.create(
                departureStationId = departureStation.stationId,
                arrivalStationId = arrivalStation.stationId
            )
            val basicFare = TestBasicFareFactory.create(
                routeId = route.routeId,
                amount = Amount(10000)
            )

            every { findBasicFareUsecase.execute(departureStation.stationId, arrivalStation.stationId) }.returns(basicFare)

            val expectedResponse = SearchFareResponse(
                routeId = route.routeId.value,
                amount = basicFare.amount.value
            )
            val expected = ObjectMapper()
                .registerKotlinModule()
                .writeValueAsString(expectedResponse)

            val mockMvc = MockMvcBuilders.standaloneSetup(basicFareController).build()

            val resultResponse = mockMvc.perform(
                get("/api/v1/catalog/fare/search")
                    .param("departureStationId", departureStation.stationId.value)
                    .param("arrivalStationId", arrivalStation.stationId.value)
            )
                .andExpect(status().isOk)
                .andReturn()
                .response

            val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)
            Assertions.assertEquals(expected, result)
        }
    }

    @Test
    fun `経路が存在しない場合は404エラーを返すこと`() {
        val departureStation = TestStationFactory.create()
        val arrivalStation = TestStationFactory.create()

        every { findBasicFareUsecase.execute(departureStation.stationId, arrivalStation.stationId) }.throws(
            AssertionFailException("指定した駅間の経路は存在しません", ErrorCode.NOTFOUND_ASSERTION)
        )

        val mockMvc = MockMvcBuilders.standaloneSetup(basicFareController)
            .setControllerAdvice(ExceptionHandler())
            .build()

        mockMvc.perform(
            get("/api/v1/catalog/fare/search")
                .param("departureStationId", departureStation.stationId.value)
                .param("arrivalStationId", arrivalStation.stationId.value)
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `経路は存在するが運賃が存在しない場合`() {
        val departureStation = TestStationFactory.create()
        val arrivalStation = TestStationFactory.create()

        every { findBasicFareUsecase.execute(departureStation.stationId, arrivalStation.stationId) }.throws(
            AssertionFailException("指定した経路の運賃は存在しません", ErrorCode.NOTFOUND_ASSERTION)
        )

        val mockMvc = MockMvcBuilders.standaloneSetup(basicFareController)
            .setControllerAdvice(ExceptionHandler())
            .build()

        mockMvc.perform(
            get("/api/v1/catalog/fare/search")
                .param("departureStationId", departureStation.stationId.value)
                .param("arrivalStationId", arrivalStation.stationId.value)
        ).andExpect(status().isNotFound)
    }
}