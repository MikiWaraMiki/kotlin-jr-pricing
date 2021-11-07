package jrpricing.demo.first.usecase.price

import io.mockk.mockk
import jrpricing.demo.first.domain.model.fare.FareCalcService
import jrpricing.demo.first.domain.model.surcharge.SurchargeCalcService
import org.junit.jupiter.api.Test

class PriceCalculationUsecaseTest {
    private val fareCalcService: FareCalcService = mockk()
    private val surchargeCalcService: SurchargeCalcService = mockk()

    @Test
    fun `存在しない出発駅を指定した場合はエラーが発生する`() {

    }
}