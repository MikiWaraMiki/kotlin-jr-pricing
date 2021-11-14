package jrpricing.catalog.presentation.api.route

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.mockk.every
import io.mockk.mockk
import jrpricing.catalog.presentation.api.dto.RouteDto
import jrpricing.catalog.presentation.api.dto.StationDto
import jrpricing.catalog.presentation.api.shared.ExceptionHandler
import jrpricing.catalog.testdata.route.TestRouteFactory
import jrpricing.catalog.testdata.station.TestStationFactory
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import jrpricing.catalog.usecase.route.FindRouteFromDepartureAndArrivalUsecase
import jrpricing.catalog.usecase.route.FindRouteFromDepartureAndArrivalUsecaseResultDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.nio.charset.StandardCharsets

internal class RouteControllerTest {
    private val findRouteFromDepartureAndArrivalUsecase: FindRouteFromDepartureAndArrivalUsecase = mockk()
    private val routeController = RouteController(findRouteFromDepartureAndArrivalUsecase)

    @Nested
    inner class SearchTest() {
        @Test
        fun `出発駅と到着駅の組み合わせの経路が存在する場合は200レスポンスを返し、レスポンスのスキーマが正しいこと`() {
            val departureStation = TestStationFactory.create()
            val arrivalStation = TestStationFactory.create()
            val route = TestRouteFactory.create(
                departureStationId = departureStation.stationId,
                arrivalStationId = arrivalStation.stationId
            )

            every { findRouteFromDepartureAndArrivalUsecase.execute(departureStation.stationId, arrivalStation.stationId) }.returns(
                FindRouteFromDepartureAndArrivalUsecaseResultDto(route.routeId, route.distance, departureStation, arrivalStation)
            )

            val expectedResponse = RouteDto(
                route.routeId.value,
                route.distance.halfTripDistance(),
                StationDto(departureStation.stationId.value, departureStation.name()),
                StationDto(arrivalStation.stationId.value, arrivalStation.name())
            )
            val expected = ObjectMapper()
                .registerKotlinModule()
                .writeValueAsString(expectedResponse)

            val mockMvc = MockMvcBuilders.standaloneSetup(routeController).build()

            val resultResponse = mockMvc.perform(
                    get("/api/v1/route/search")
                        .param("departureStationId", departureStation.stationId.value)
                        .param("arrivalStationId", arrivalStation.stationId.value)
                )
                .andExpect(status().isOk)
                .andReturn()
                .response

            val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)
            Assertions.assertEquals(expected, result)
        }

        @Test
        fun `経路が存在しない場合は404エラーを返す`() {
            val departureStation = TestStationFactory.create()
            val arrivalStation = TestStationFactory.create()

            every { findRouteFromDepartureAndArrivalUsecase.execute(departureStation.stationId, arrivalStation.stationId) }.throws(
                AssertionFailException("経路が存在しません", ErrorCode.NOTFOUND_ASSERTION)
            )

            val mockMvc = MockMvcBuilders.standaloneSetup(routeController)
                .setControllerAdvice(ExceptionHandler())
                .build()

            mockMvc.perform(
                get("/api/v1/route/search")
                    .param("departureStationId", departureStation.stationId.value)
                    .param("arrivalStationId", arrivalStation.stationId.value)
            ).andExpect(status().isNotFound)

        }
    }
}