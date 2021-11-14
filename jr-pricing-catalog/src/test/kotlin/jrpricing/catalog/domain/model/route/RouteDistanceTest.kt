package jrpricing.catalog.domain.model.route

import jrpricing.catalog.domain.exception.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RouteDistanceTest {
    @Test
    fun `移動距離が1キロ未満の場合はDomainExceptionが発生すること`() {
        val target: () -> Unit = {
            RouteDistance(0)
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("移動距離は1キロ以上である必要があります", exception.message)
    }

    @Test
    fun `移動距離が1キロ以上の場合はオブジェクトが生成され、片道の距離と往復距離を取得できること`() {
        val distance = RouteDistance(1)

        Assertions.assertEquals(1, distance.halfTripDistance())
        Assertions.assertEquals(2, distance.roundTripDistance())
    }
}