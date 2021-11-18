package jrpricing.catalog.infra.repository

import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.domain.model.surcharge.BasicSurcharge
import jrpricing.catalog.domain.model.surcharge.BasicSurchargeRepository
import jrpricing.db.jooq.gen.tables.TblBasicFares
import jrpricing.db.jooq.gen.tables.TblBasicSurcharges
import jrpricing.db.jooq.gen.tables.records.TblBasicSurchargesRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BasicSurchargeJooqRepository(
    private val jooq: DSLContext
): BasicSurchargeRepository {
    override fun insert(basicSurcharge: BasicSurcharge) {
        val record = jooq.newRecord(TblBasicSurcharges.TBL_BASIC_SURCHARGES)
        record.routeId = basicSurcharge.routeId.value
        record.amount = basicSurcharge.amount.value
        record.insert()
    }

    override fun findByRouteId(routeId: RouteId): BasicSurcharge? {
        val table = TblBasicSurcharges.TBL_BASIC_SURCHARGES
        val basicSurchargeRecord = jooq.selectFrom(table)
            .where(table.ROUTE_ID.eq(routeId.value))
            .fetchOne() ?: return null

        return mapBasicSurchargeToEntity(basicSurchargeRecord)
    }

    private fun mapBasicSurchargeToEntity(record: TblBasicSurchargesRecord): BasicSurcharge {
        return BasicSurcharge.reConstructor(
            RouteId.reConstructor(record.routeId),
            Amount(record.amount)
        )
    }
}