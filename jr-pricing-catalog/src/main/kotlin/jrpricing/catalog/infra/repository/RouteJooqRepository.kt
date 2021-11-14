package jrpricing.catalog.infra.repository

import jrpricing.catalog.domain.model.route.Route
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.db.jooq.gen.tables.TblRoutes
import jrpricing.db.jooq.gen.tables.records.TblRoutesRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class RouteJooqRepository(
    private val jooq: DSLContext
): RouteRepository {

    override fun insert(route: Route) {
        val record = jooq.newRecord(TblRoutes.TBL_ROUTES)
        record.id = route.routeId.value
        record.departureStationId = route.departureStationId.value
        record.arrivalStationId = route.arrivalStationId.value

        record.insert()
    }


    override fun findByDepartureAndArrivalStation(departureStationId: StationId, arrivalStationId: StationId): Route? {
        val table = TblRoutes.TBL_ROUTES
        val routeRecord: TblRoutesRecord = jooq.selectFrom(table)
            .where(
                table.DEPARTURE_STATION_ID.eq(departureStationId.value)
                    .and(
                        table.ARRIVAL_STATION_ID.eq(arrivalStationId.value)
                    )
            )
            .fetchOne() ?: return null

        return mapRouteRecordToEntity(routeRecord)
    }

    private fun mapRouteRecordToEntity(routeRecord: TblRoutesRecord): Route {
        return Route(
            routeId = RouteId.reConstructor(routeRecord.id),
            departureStationId = StationId.reConstructor(routeRecord.departureStationId),
            arrivalStationId = StationId.reConstructor(routeRecord.arrivalStationId)
        )
    }
}