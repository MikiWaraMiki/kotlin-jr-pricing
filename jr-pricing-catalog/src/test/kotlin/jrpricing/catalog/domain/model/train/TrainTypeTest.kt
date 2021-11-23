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
        val hikarTrainType = TrainType.fromId(TrainType.HIKARI.id)

        Assertions.assertEquals(TrainType.HIKARI, hikarTrainType)
        Assertions.assertFalse(hikarTrainType.isNozomi())
    }
}