package domain.model.discount

import domain.model.route.RouteDistanceTable
import domain.model.ticket.Ticket
import domain.model.ticket.TicketType
import java.lang.Exception

/**
 * 移動距離に応じて割引ルールを適用するクラス
 */
class DistanceKilometerDiscountRule(private val ticket: Ticket): DiscountRule {

    /**
     * 片道の移動距離に応じて割引が適用可能か確かめる
     */
    override fun isAbleEnabled(): Boolean {
        if (ticket.ticketType == TicketType.ONE_WAY) return  false

        val halfDistance = RouteDistanceTable.distance(ticket.route)

        // 片道601km未満は適用対象外
        if (halfDistance < 601) return false

        return true
    }
}