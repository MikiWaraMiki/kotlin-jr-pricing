package jrpricing.catalog.domain.model.surcharge

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount
import org.junit.jupiter.api.*

internal class BasicSurchargeTest {

    @Nested
    @DisplayName("不変条件テスト")
    inner class InitializeTest() {
        @Test
        fun `特急料金に5円単位が利用されている場合はエラーが発生する`() {
            val routeId = RouteId.create()
            val amount = Amount(5)

            val target: () -> Unit = {
                BasicSurcharge.reConstructor(routeId, amount)
            }

            val exception = assertThrows<DomainException>(target)

            Assertions.assertEquals("特急料金は10円単位である必要があります", exception.message)
        }

        @Test
        fun `特急料金に1円単位が利用されている場合はエラーが発生する`() {
            val routeId = RouteId.create()
            val amount = Amount(11)

            val target: () -> Unit = {
                BasicSurcharge.reConstructor(routeId, amount)
            }

            val exception = assertThrows<DomainException>(target)

            Assertions.assertEquals("特急料金は10円単位である必要があります", exception.message)
        }

        @Test
        fun `特急料金が10円単位であればエラーが発生しないこと`() {
            val routeId = RouteId.create()
            val amount = Amount(10)

            val basicSurcharge = BasicSurcharge.reConstructor(routeId, amount)

            Assertions.assertEquals(amount, basicSurcharge.amount)
        }
    }

    @Nested
    @DisplayName("addNozomiAdditionalChargeのテスト")
    inner class AddNozomiAdditionalCharge() {
        @Test
        fun `経路が一致する場合は、加算した結果になること`() {
            val routeId = RouteId.create()
            val amount = Amount(10)

            val basicSurcharge = BasicSurcharge.reConstructor(routeId, amount)
            val nozomiAdditionalCharge = NozomiAdditionalCharge.reConstructor(routeId, amount)

            val result = basicSurcharge.addNozomiAdditionalCharge(nozomiAdditionalCharge)

            Assertions.assertEquals(Amount(20), result.amount)
        }

        @Test
        fun `経路が一致しない場合は、DomainExceptionが発生すること`() {
            val routeId = RouteId.create()
            val otherRouteId = RouteId.create()
            val amount = Amount(10)

            val basicSurcharge = BasicSurcharge.reConstructor(routeId, amount)
            val nozomiAdditionalCharge = NozomiAdditionalCharge.reConstructor(otherRouteId, amount)

            val target: () -> Unit = {
                basicSurcharge.addNozomiAdditionalCharge(nozomiAdditionalCharge)
            }

            val exception = assertThrows<DomainException>(target)

            Assertions.assertEquals("経路が一致しないためのぞみ利用時の料金を加算することができません", exception.message)
        }
    }
}