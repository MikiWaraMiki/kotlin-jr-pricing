package domain.model.ticket

import domain.model.route.Route
import domain.model.train.SeatType
import domain.model.train.TrainType

/**
 * 購入切符クラス
 */
class Ticket(
    val route: Route,
    val ticketType: TicketType,
    val trainType: TrainType,
    val seatType: SeatType,
    val isChild: Boolean
) {

    fun isOneway(): Boolean {
        return ticketType == TicketType.ONE_WAY
    }

    fun isLongDistance(): Boolean {
        return route.isLongDistance()
    }
}