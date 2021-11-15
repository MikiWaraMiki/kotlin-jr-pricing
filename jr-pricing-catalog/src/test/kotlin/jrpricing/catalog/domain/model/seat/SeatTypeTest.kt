package jrpricing.catalog.domain.model.seat

import jrpricing.catalog.domain.exception.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class SeatTypeTest {
    @Nested
    inner class FromIdTest {
        @Test
        fun `IDが1の場合は指定席を取得できること`() {
            val seatType = SeatType.fromId(1)

            Assertions.assertEquals("指定席", seatType.value)
        }

        @Test
        fun `IDが2の場合は自由席を取得できること`() {
            val seatType = SeatType.fromId(2)

            Assertions.assertEquals("自由席", seatType.value)
        }

        @Test
        fun `存在しないIDの場合はエラーが発生すること`() {
            val target: () -> Unit = {
                SeatType.fromId(3)
            }

            val exception = assertThrows<DomainException>(target)

            Assertions.assertEquals("存在しない席種別です", exception.message)
        }
    }
}