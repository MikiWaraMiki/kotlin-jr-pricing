package jrpricing.catalog.domain.model.train

import jrpricing.catalog.domain.exception.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TrainTypeTest {
    @Nested
    inner class FromIdTest() {
        @Test
        fun `IDが1の場合は、のぞみを取得できること`() {
            val trainType = TrainType.fromId(1)

            Assertions.assertEquals(TrainType.NOZOMI, trainType)
        }

        @Test
        fun `IDが2の場合は、ひかりを取得できること`() {
            val trainType = TrainType.fromId(2)

            Assertions.assertEquals(TrainType.HIKARI, trainType)
        }

        @Test
        fun `存在しないIDの場合は、DomainExceptionが発生すること`() {
            val target: () -> Unit = {
                TrainType.fromId(3)
            }

            val exception = assertThrows<DomainException>(target)

            Assertions.assertEquals("存在しない列車種別です", exception.message)
        }
    }
}