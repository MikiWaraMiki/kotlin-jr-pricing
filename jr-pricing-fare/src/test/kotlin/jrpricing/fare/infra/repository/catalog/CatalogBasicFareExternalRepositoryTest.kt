package jrpricing.fare.infra.repository.catalog

import io.mockk.every
import io.mockk.mockk
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.infra.external.CatalogApiClient
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@ExtendWith(SpringExtension::class)
@SpringJUnitConfig
@SpringBootTest
internal class CatalogBasicFareExternalRepositoryTest() {
    val mockCatalogApiClient: CatalogApiClient = mockk()

    @Nested
    inner class FindBasicFareTest() {
        @Test
        fun `経路が存在する発着駅の場合は、運賃の料金を返す`() {
            every { mockCatalogApiClient.callGet(any()) }.returns(
                "{\"routeId\": \"sample-ulid\", \"amount\": 18000}"
            )

            val catalogBasicFareExternalRepository = CatalogBasicFareExternalRepository(mockCatalogApiClient, "http://localhost:9200")

            val expected = Amount.of(18000)

            val result = catalogBasicFareExternalRepository.findBasicFare("sample", "sample")

            Assertions.assertEquals(expected, result)
        }
    }
}