package domain.model.fare

import domain.model.shared.Passengers
import domain.model.shared.Price

class FareCalcResult (
    private val fare: Fare,
    private val passengers: Passengers
){
    fun total(): Price {
        val adultTotal = fare.price(false).value * passengers.adults
        val childTotal = fare.price(true).value * passengers.childs

        return Price(adultTotal + childTotal)
    }
}