package jrpricing.discount.infra.repository

import com.github.guepardoapps.kulid.ULID
import io.mockk.every
import io.mockk.mockk
import jrpricing.discount.domain.route.BusinessKilometer
import jrpricing.discount.domain.route.Route
import jrpricing.discount.infra.external.ExternalApiClient
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@ExtendWith(SpringExtension::class)
@SpringJUnitConfig
@SpringBootTest
internal class RouteExternalRepositoryTest {
    private val mockApiClient: ExternalApiClient = mockk()

    @Test
    fun `カタログAPIから200レスポンスが帰ってきた場合はRouteオブジェクトを返すこと`() {
        every { mockApiClient.callGet(any()) }.returns(
            "{\"id\": \"uuid\", \"distance\": 601, \"departureStation\": {\"id\": \"uuid\", \"name\": \"tokyo\"}, \"arrivalStation\": {\"id\": \"uuid\", \"name\": \"shin_osaka\"} }"
        )

        val routeExternalRepository = RouteExternalRepository(
            mockApiClient,
            "http://localhost:9200"
        )

        val expected = Route(BusinessKilometer(601))

        val result = routeExternalRepository.findByStationId(ULID.random(), ULID.random())

        Assertions.assertEquals(expected.kilometer(), result!!.kilometer())
    }
}