package jrpricing.fare.infra.repository.catalog

import com.github.guepardoapps.kulid.ULID
import io.mockk.every
import io.mockk.mockk
import jrpricing.fare.domain.fare.BasicFare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.TripRoute
import jrpricing.fare.infra.external.ExternalApiClient
import jrpricing.fare.infra.external.exception.CatalogApiClientException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@ExtendWith(SpringExtension::class)
@SpringJUnitConfig
@SpringBootTest
internal class CatalogBasicFareExternalRepositoryTest() {
    private val mockCatalogApiClient: ExternalApiClient = mockk()

    private val tripRoute = TripRoute(ULID.random(), ULID.random())

    @Nested
    inner class FindBasicFareTest() {
        @Test
        fun `カタログAPIから正常にレスポンスが返されたら、運賃の料金を返す`() {
            every { mockCatalogApiClient.callGet(any()) }.returns(
                "{\"routeId\": \"sample-ulid\", \"amount\": 18000}"
            )

            val catalogBasicFareExternalRepository = CatalogBasicFareExternalRepository(mockCatalogApiClient, "http://localhost:9200")

            val expected = BasicFare.of(Amount.of(18000))

            val result = catalogBasicFareExternalRepository.findBasicFare(tripRoute)

            Assertions.assertEquals(expected.amount, result.amount)
        }

        @Test
        fun `カタログAPIから404エラーが返されたら、CatalogApiExceptionが発生する`() {
            every { mockCatalogApiClient.callGet(any()) }.throws(
                CatalogApiClientException("エラーが発生しました", HttpStatus.NOT_FOUND)
            )

            val catalogBasicFareExternalRepository = CatalogBasicFareExternalRepository(mockCatalogApiClient, "http://localhost:9200")

            val target:() -> Unit = {
                catalogBasicFareExternalRepository.findBasicFare(tripRoute)
            }

            val exception = assertThrows<CatalogApiClientException>(target)

            Assertions.assertEquals("エラーが発生しました", exception.message)
            Assertions.assertEquals(exception.httpStatus, HttpStatus.NOT_FOUND)
        }

        @Test
        fun `カタログAPIから500エラーが返されたら、CatalogApiExceptionが発生すること`() {
            every { mockCatalogApiClient.callGet(any()) }.throws(
                CatalogApiClientException("エラーが発生しました", HttpStatus.INTERNAL_SERVER_ERROR)
            )

            val catalogBasicFareExternalRepository = CatalogBasicFareExternalRepository(mockCatalogApiClient, "http://localhost:9200")

            val target:() -> Unit = {
                catalogBasicFareExternalRepository.findBasicFare(tripRoute)
            }

            val exception = assertThrows<CatalogApiClientException>(target)

            Assertions.assertEquals("エラーが発生しました", exception.message)
            Assertions.assertEquals(exception.httpStatus, HttpStatus.INTERNAL_SERVER_ERROR)

        }
    }
}