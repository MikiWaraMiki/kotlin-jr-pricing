package jrpricing.demo.first.domain.model.ticket

import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.train.SeatType
import jrpricing.demo.first.domain.model.train.TrainType

/**
 * 購入切符クラス
 */
class Ticket(
    val route: Route,
    val departureDate: DepartureDate,
    val tripType: TripType,
    val trainType: TrainType,
    val seatType: SeatType,
    val isChild: Boolean
) {

    fun isOneway(): Boolean {
        return tripType == TripType.ONE_WAY
    }

    fun isLongDistance(): Boolean {
        return route.isLongDistance()
    }

    fun useNozomi(): Boolean {
        return trainType == TrainType.NOZOMI
    }

    fun useReservedSeat(): Boolean {
        return seatType == SeatType.RESERVED
    }

    fun useNonReservedSeat(): Boolean {
        return seatType == SeatType.NON_RESERVED
    }
}