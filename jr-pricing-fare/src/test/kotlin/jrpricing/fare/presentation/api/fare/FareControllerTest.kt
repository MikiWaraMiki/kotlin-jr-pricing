package jrpricing.fare.presentation.api.fare

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.guepardoapps.kulid.ULID
import io.mockk.every
import io.mockk.mockk
import jrpricing.fare.domain.fare.Fare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.TripRoute
import jrpricing.fare.domain.shared.TripType
import jrpricing.fare.presentation.api.shared.ExceptionHandler
import jrpricing.fare.usecase.exception.AssertionFailException
import jrpricing.fare.usecase.exception.ErrorCode
import jrpricing.fare.usecase.fare.FindFareByStationIDUsecase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.lang.Exception
import java.nio.charset.StandardCharsets

class FareControllerTest {
    @Nested
    @DisplayName("searchメソッドのテスト")
    inner class SearchTest() {
        private val findFareByStationIDUsecase: FindFareByStationIDUsecase = mockk()
        private val fareController = FareController(findFareByStationIDUsecase)

        @Test
        fun `出発駅と到着駅の経路が存在し、運賃の金額を200レスポンスで返すこと`() {
            val departureStationId = ULID.random()
            val arrivalStationId = ULID.random()
            val tripType = TripType.ROUND_TRIP

            val fare = Fare.of(Amount.of(9000))


            every { findFareByStationIDUsecase.execute(tripRoute = any(), tripType) }.returns(
                fare
            )

            val expectedResponse = SearchResponse(
                amount = fare.amount.value
            )
            val expected = ObjectMapper().registerKotlinModule().writeValueAsString(expectedResponse)

            val mockMvc = MockMvcBuilders.standaloneSetup(fareController).build()

            val resultResponse = mockMvc.perform(
                get("/api/v1/fare/search")
                    .param("departureStationId", departureStationId)
                    .param("arrivalStationId", arrivalStationId)
                    .param("tripTypeName", tripType.typeName)
            )
                .andExpect(status().isOk)
                .andReturn()
                .response

            val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)
            Assertions.assertEquals(expected, result)
        }

        @Test
        fun `出発駅と到着駅の経路が存在しない場合は、404エラーを返すこと`() {
            val departureStationId = ULID.random()
            val arrivalStationId = ULID.random()
            val tripType = TripType.ROUND_TRIP

            every { findFareByStationIDUsecase.execute(tripRoute = any(), tripType) }.throws(
                AssertionFailException("存在しない経路です", ErrorCode.NOTFOUND_ASSERTION)
            )

            val mockMvc = MockMvcBuilders.standaloneSetup(fareController)
                .setControllerAdvice(ExceptionHandler())
                .build()

            mockMvc.perform(
                get("/api/v1/fare/search")
                    .param("departureStationId", departureStationId)
                    .param("arrivalStationId", arrivalStationId)
                    .param("tripTypeName", tripType.typeName)
            ).andExpect(status().isNotFound)
        }

        @Test
        fun `運賃の割引計算に失敗した場合は、500エラーを返すこと`() {
            val departureStationId = ULID.random()
            val arrivalStationId = ULID.random()
            val tripType = TripType.ROUND_TRIP

            every { findFareByStationIDUsecase.execute(tripRoute = any(), tripType) }.throws(
                Exception("割引計算に失敗しました")
            )

            val mockMvc = MockMvcBuilders.standaloneSetup(fareController)
                .setControllerAdvice(ExceptionHandler())
                .build()

            mockMvc.perform(
                get("/api/v1/fare/search")
                    .param("departureStationId", departureStationId)
                    .param("arrivalStationId", arrivalStationId)
                    .param("tripTypeName", tripType.typeName)
            ).andExpect(status().isInternalServerError)

        }
    }
}