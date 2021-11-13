package jrpricing.catalog.domain.model.station

import jrpricing.demo.first.domain.model.exception.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class StationNameTest {

    @Test
    fun `駅名が入力されていない場合はエラーが発生すること`() {
        val target: () -> Unit = {
            StationName("")
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("駅名が入力されていません", exception.message)
    }

    @Test
    fun `駅名が51文字以上で入力されている場合はエラーが発生すること`() {
        val target: () -> Unit = {
            StationName("a".repeat(51))
        }

        val exception = assertThrows<DomainException>(target)

        Assertions.assertEquals("駅名は50文字以内で入力してください", exception.message)
    }

    @Test
    fun `駅名が50文字以内で入力されている場合はエラーが発生しないこと`() {
        val target: () -> Unit = {
            StationName("a".repeat(50))
        }

        assertDoesNotThrow(target)
    }
}