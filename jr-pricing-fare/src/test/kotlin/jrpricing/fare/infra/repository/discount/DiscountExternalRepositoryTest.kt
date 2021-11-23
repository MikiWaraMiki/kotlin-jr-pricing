package jrpricing.fare.infra.repository.discount

import com.github.guepardoapps.kulid.ULID
import io.mockk.every
import io.mockk.mockk
import jrpricing.fare.domain.fare.BasicFare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.DepartureDate
import jrpricing.fare.domain.shared.Passenger
import jrpricing.fare.domain.shared.TripRoute
import jrpricing.fare.infra.external.ExternalApiClient
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@SpringJUnitConfig
@SpringBootTest
internal class DiscountExternalRepositoryTest {
    private val mockExternalApiClient: ExternalApiClient = mockk()

    @Nested
    inner class CalcTest() {
        val basicFare = BasicFare.of(Amount.of(10000))
        val tripRoute = TripRoute(ULID.random(), ULID.random())
        val passenger = Passenger(1, 0)
        val departureDate = DepartureDate(LocalDate.now())

        @Test
        fun `APIから200レスポンスが返された場合は、Amountを取得できること`() {
            every { mockExternalApiClient.callGet(any()) }.returns(
                "{\"amount\": 5000}"
            )

            val discountExternalRepository = DiscountExternalRepository(
                mockExternalApiClient,
                "http://localhost:9200"
            )

            val expected = Amount.of(5000)

            val result = discountExternalRepository.calc(
                basicFare,
                tripRoute,
                passenger,
                departureDate
            )

            Assertions.assertEquals(expected, result)
        }
    }
}