package domain.model.discount

import domain.model.discount.distance.DistanceKilometerDiscountRule
import domain.model.discount.group.GroupDiscountRule
import domain.model.discount.largegroup.LargeGroupDiscountRule
import domain.model.route.Route
import domain.model.station.Station
import io.mockk.mockk

class FareDiscountCalcServiceTest {
    private val mockDistanceDiscountRule: DistanceKilometerDiscountRule = mockk()
    private val mockGroupDiscountRule: GroupDiscountRule = mockk()
    private val mockLargeGroupDiscountRule: LargeGroupDiscountRule = mockk()
}