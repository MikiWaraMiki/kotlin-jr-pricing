package jrpricing.catalog.infra.repository

import jrpricing.catalog.domain.model.fare.BasicFare
import jrpricing.catalog.domain.model.fare.BasicFareRepository
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.db.jooq.gen.tables.TblBasicFares
import jrpricing.db.jooq.gen.tables.records.TblBasicFaresRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BasicFareJooqRepository(
    private val jooq: DSLContext
): BasicFareRepository {
    override fun insert(basicFare: BasicFare) {
        val record = jooq.newRecord(TblBasicFares.TBL_BASIC_FARES)
        record.routeId = basicFare.routeId.value
        record.amount = basicFare.amount.value
        record.insert()
    }

    override fun findByRouteId(routeId: RouteId): BasicFare? {
        val table = TblBasicFares.TBL_BASIC_FARES
        val basicFareRecord = jooq.selectFrom(table)
            .where(table.ROUTE_ID.eq(routeId.value))
            .fetchOne() ?: return null

        return mapBasicFareRecordToEntity(basicFareRecord)
    }

    private fun mapBasicFareRecordToEntity(basicFaresRecord: TblBasicFaresRecord): BasicFare {
        return BasicFare.reConstructor(
            routeId = RouteId.reConstructor(basicFaresRecord.routeId),
            amount = Amount(basicFaresRecord.amount)
        )
    }
}