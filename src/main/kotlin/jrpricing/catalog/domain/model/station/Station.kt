package jrpricing.catalog.domain.model.station

/**
 * 駅
 */
class Station private constructor(
    val stationId: StationId,
    val stationName: StationName
) {

    companion object {
        fun reConstructor(stationId: StationId, stationName: StationName): Station {
            return Station(
                stationId,
                stationName
            )
        }
    }
}