package jrpricing.discount.presentation.api.groupdiscount

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.guepardoapps.kulid.ULID
import io.mockk.every
import io.mockk.mockk
import jrpricing.discount.domain.shared.Amount
import jrpricing.discount.domain.shared.TripType
import jrpricing.discount.usecase.roundtrip.CalcRoundTripDiscountUsecase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.nio.charset.StandardCharsets

internal class GroupDiscountControllerTest {
    @Nested
    inner class CalcTest() {
        private val calcRoundTripDiscountUsecase: CalcRoundTripDiscountUsecase = mockk()
        private val groupDiscountController = GroupDiscountController(calcRoundTripDiscountUsecase)

        @Test
        fun `出発駅と到着駅の経路が存在し、割引結果を200レスポンスで返すこと`() {
            val departureStationId = ULID.random()
            val arrivalStationId = ULID.random()
            val tripType = TripType.ROUND_TRIP
            val fare = 10010

            every { calcRoundTripDiscountUsecase.execute(departureStationId, arrivalStationId, tripType, fare) }.returns(
                Amount.of(9000)
            )

            val expectedResponse = GroupDiscountCalcResponse(
                amount = 9000
            )
            val expected = ObjectMapper().registerKotlinModule().writeValueAsString(expectedResponse)

            val mockMvc = MockMvcBuilders.standaloneSetup(groupDiscountController).build()

            val resultResponse = mockMvc.perform(
                get("/api/v1/discount/group_discount/calc")
                    .param("departureStationId", departureStationId)
                    .param("arrivalStationId", arrivalStationId)
                    .param("tripTypeName", tripType.typeName)
                    .param("fare", fare.toString())
            )
                .andExpect(status().isOk)
                .andReturn()
                .response

            val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)
            Assertions.assertEquals(expected, result)
        }
    }
}