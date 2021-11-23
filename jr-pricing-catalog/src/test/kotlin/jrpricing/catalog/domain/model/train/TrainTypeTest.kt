package jrpricing.catalog.domain.model.train

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TrainTypeTest {
    @Test
    fun `のぞみのオブジェクトをIDから取得できること`() {
        val nozomiTrainType = TrainType.fromId(TrainType.NOZOMI.id)

        Assertions.assertEquals(TrainType.NOZOMI, nozomiTrainType)
        Assertions.assertTrue(nozomiTrainType.isNozomi())
    }

    @Test
    fun `ひかりのオブジェクトをIDから取得できること`() {
        val hikariTrainType = TrainType.fromId(TrainType.HIKARI.id)

        Assertions.assertEquals(TrainType.HIKARI, hikariTrainType)
        Assertions.assertFalse(hikariTrainType.isNozomi())
    }
}