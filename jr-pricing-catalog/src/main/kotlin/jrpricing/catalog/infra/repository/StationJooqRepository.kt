package jrpricing.catalog.infra.repository

import jrpricing.db.jooq.gen.tables.records.TblStationsRecord

import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.model.station.StationName
import jrpricing.catalog.domain.repository.station.StationRepository
import jrpricing.db.jooq.gen.tables.TblStations
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

/**
 * 駅リポジトリ jooq実装クラス
 */
@Repository
class StationJooqRepository(
    private val jooq: DSLContext
): StationRepository {

    override fun insert(station: Station) {
        val record = jooq.newRecord(TblStations.TBL_STATIONS)
        record.id = station.stationId.value
        record.name = station.name()

        record.insert()
    }


    override fun findById(stationId: StationId): Station? {
        val table = TblStations.TBL_STATIONS
        val stationsRecord: TblStationsRecord = jooq.selectFrom(table)
            .where(table.ID.eq(stationId.value))
            .fetchOne() ?: return null

        return mapRecordToEntity(stationsRecord)
    }

    private fun mapRecordToEntity(stationsRecord: TblStationsRecord): Station {
        return Station.reConstructor(
            StationId.reConstructor(stationsRecord.id),
            StationName(stationsRecord.name)
        )
    }
}