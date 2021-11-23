package jrpricing.catalog.infra.repository

import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.domain.model.surcharge.NozomiAdditionalCharge
import jrpricing.catalog.domain.model.surcharge.NozomiAdditionalChargeRepository
import jrpricing.db.jooq.gen.tables.TblNozomiAdditionalCharges
import jrpricing.db.jooq.gen.tables.records.TblNozomiAdditionalChargesRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import kotlin.math.round

@Repository
class NozomiAdditionalChargeJooqRepository(
    private val jooq: DSLContext
): NozomiAdditionalChargeRepository {

    override fun insert(nozomiAdditionalCharge: NozomiAdditionalCharge) {
        val record = jooq.newRecord(TblNozomiAdditionalCharges.TBL_NOZOMI_ADDITIONAL_CHARGES)
        record.routeId = nozomiAdditionalCharge.routeId.value
        record.amount = nozomiAdditionalCharge.amount.value
        record.insert()
    }

    override fun findByRouteId(routeId: RouteId): NozomiAdditionalCharge? {
        val table = TblNozomiAdditionalCharges.TBL_NOZOMI_ADDITIONAL_CHARGES
        val nozomiAdditionalChargeRecord = jooq.selectFrom(table)
            .where(table.ROUTE_ID.eq(routeId.value))
            .fetchOne() ?: return null

        return mapNozomiAdditionalChargeRecordToEntity(nozomiAdditionalChargeRecord)
    }

    private fun mapNozomiAdditionalChargeRecordToEntity(record: TblNozomiAdditionalChargesRecord): NozomiAdditionalCharge {
        return NozomiAdditionalCharge.reConstructor(
            routeId = RouteId.reConstructor(record.routeId),
            amount = Amount(record.amount)
        )
    }
}