package jrpricing.fare.usecase.fare

import com.github.guepardoapps.kulid.ULID
import io.mockk.every
import io.mockk.mockk
import jrpricing.fare.domain.catalog.CatalogBasicFareRepository
import jrpricing.fare.domain.discount.DiscountRepository
import jrpricing.fare.domain.fare.BasicFare
import jrpricing.fare.domain.fare.Fare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.TripRoute
import jrpricing.fare.domain.shared.TripType
import jrpricing.fare.usecase.exception.AssertionFailException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception

internal class FindFareByStationIDUsecaseTest {
    private val mockCatalogBasicFareRepository: CatalogBasicFareRepository = mockk()
    private val mockDiscountRepository: DiscountRepository = mockk()

    @Test
    fun `指定された経路の運賃取得と割引結果の取得ができる場合は運賃を返すこと`() {
        val tripRoute = TripRoute(ULID.random(), ULID.random())
        val tripType = TripType.ROUND_TRIP
        val basicFare = BasicFare.of(Amount.of(10000))

        every { mockCatalogBasicFareRepository.findBasicFare(tripRoute) }.returns(basicFare)
        every { mockDiscountRepository.calc(basicFare, tripRoute, tripType) }.returns(
            Amount.of(9000)
        )

        val usecase = FindFareByStationIDUsecase(mockCatalogBasicFareRepository, mockDiscountRepository)

        val result = usecase.execute(tripRoute, tripType)

        val expected = Fare.of(Amount.of(9000))

        Assertions.assertEquals(expected.amount.value, result.amount.value)
    }

    @Test
    fun `指定された経路の運賃が存在しない場合は、AssertionFailExceptionが発生すること`() {
        val tripRoute = TripRoute(ULID.random(), ULID.random())
        val tripType = TripType.ROUND_TRIP
        val basicFare = BasicFare.of(Amount.of(10000))

        every { mockCatalogBasicFareRepository.findBasicFare(tripRoute) }.returns(null)

        val usecase = FindFareByStationIDUsecase(mockCatalogBasicFareRepository, mockDiscountRepository)

        val target: () -> Unit = {
            usecase.execute(tripRoute, tripType)
        }

        val exception = assertThrows<AssertionFailException>(target)

        Assertions.assertEquals("指定された経路は登録されていません", exception.message)
    }

    @Test
    fun `運賃割引結果の算出に失敗した場合は、Exceptionが発生すること`() {
        val tripRoute = TripRoute(ULID.random(), ULID.random())
        val tripType = TripType.ROUND_TRIP
        val basicFare = BasicFare.of(Amount.of(10000))

        every { mockCatalogBasicFareRepository.findBasicFare(tripRoute) }.returns(basicFare)
        every { mockDiscountRepository.calc(basicFare, tripRoute, tripType) }.returns(
            null
        )

        val usecase = FindFareByStationIDUsecase(mockCatalogBasicFareRepository, mockDiscountRepository)

        val target: () -> Unit = {
            usecase.execute(tripRoute, tripType)
        }

        val exception = assertThrows<Exception>(target)

        Assertions.assertEquals("割引金額の算出に失敗しました", exception.message)

    }
}