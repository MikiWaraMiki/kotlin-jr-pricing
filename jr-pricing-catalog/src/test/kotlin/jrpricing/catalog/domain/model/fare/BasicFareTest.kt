package jrpricing.catalog.domain.model.fare

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.model.route.Route
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BasicFareTest {
    @Test
    fun `運賃に5円が利用されている場合はエラーが発生すること`() {
        val routeId = RouteId.create()
        val amount = Amount(15)

        val target: () -> Unit = {
            BasicFare.reConstructor(routeId, amount)
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("運賃は10円単位である必要があります", exception.message)
    }

    @Test
    fun `運賃に1円が利用されている場合はエラーが発生すること`() {
        val routeId = RouteId.create()
        val amount = Amount(11)

        val target: () -> Unit = {
            BasicFare.reConstructor(routeId, amount)
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("運賃は10円単位である必要があります", exception.message)
    }

    @Test
    fun `運賃が10円単位である場合は、オブジェクトが生成されること`() {
        val routeId = RouteId.create()
        val amount = Amount(10)

        val basicFare = BasicFare.reConstructor(routeId, amount)

        Assertions.assertEquals(routeId, basicFare.routeId)
        Assertions.assertEquals(amount, basicFare.amount)
    }
}