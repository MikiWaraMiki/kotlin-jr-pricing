package domain.model.ticket

/**
 * 購入切符クラス
 */
class Ticket(
    private val ticketType: TicketType,
    private val isChild: Boolean
) {
}