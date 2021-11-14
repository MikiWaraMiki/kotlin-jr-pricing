package jrpricing.catalog.presentation.api.station

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.mockk.every
import io.mockk.mockk
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.presentation.api.shared.ExceptionHandler
import jrpricing.catalog.testdata.station.TestStationFactory
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import jrpricing.catalog.usecase.station.FetchByStationIdUsecase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.nio.charset.StandardCharsets

internal class StationControllerTest {
    private val fetchByStationIdUsecase: FetchByStationIdUsecase = mockk()
    private val stationController = StationController(fetchByStationIdUsecase)

    @Nested
    inner class FindStationByIdTest() {
        @Test
        fun `存在するIDを指定した場合は200レスポンスを返し、返却オブジェクトが正しいこと`() {
            val station = TestStationFactory.create()

            every { fetchByStationIdUsecase.execute(station.stationId) }.returns(station)

            val expectedResponse = FindStationByIdResponse(station.stationId.value, station.name())
            val expected = ObjectMapper()
                .registerKotlinModule()
                .writeValueAsString(expectedResponse)

            val mockMvc = MockMvcBuilders.standaloneSetup(stationController).build()

            val resultResponse = mockMvc.perform(
                    get("/api/v1/station").param("stationId", station.stationId.value)
                )
                .andExpect(status().isOk)
                .andReturn()
                .response

            val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)
            Assertions.assertEquals(expected, result)
        }

        @Test
        fun `存在しないIDを指定した場合は404レスポンスを返し、返却オブジェクトが正しいこと`() {
            val stationId = StationId.create()

            every { fetchByStationIdUsecase.execute(stationId) }.throws(
                AssertionFailException(
                    "指定した駅は存在しません",
                    ErrorCode.NOTFOUND_ASSERTION
                )
            )

            val mockMvc = MockMvcBuilders.standaloneSetup(stationController)
                .setControllerAdvice(ExceptionHandler())
                .build()

            mockMvc.perform(
                get("/api/v1/station").param("stationId", stationId.value)
            ).andExpect(status().isNotFound)
        }
    }
}