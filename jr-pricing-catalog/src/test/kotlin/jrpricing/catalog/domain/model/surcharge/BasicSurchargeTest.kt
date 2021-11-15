package jrpricing.catalog.domain.model.surcharge

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BasicSurchargeTest {
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