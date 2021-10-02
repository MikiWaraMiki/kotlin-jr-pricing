package domain.model.route

import domain.model.station.Station

/**
 * 経路の移動距離テーブル
 */
class RouteDistanceTable {
    private val distance = mapOf<Route, Int>(
        Route(Station.TOKYO, Station.SHIN_OSAKA) to 553,
        Route(Station.TOKYO, Station.HIMEJI) to 644
    )

    companion object {
        fun distance(route: Route): Int {
            val table = RouteDistanceTable()

            val distance = table.distance[route] ?: throw IllegalArgumentException("登録されていない経路です")

            return distance
        }
    }
}