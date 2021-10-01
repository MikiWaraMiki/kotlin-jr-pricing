package domain.model.ticket

import domain.model.shared.Route
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
}