package jrpricing.catalog.domain.model.station

/**
 * 駅
 */
class Station private constructor(
    private val stationId: StationId,
) {

    companion object {
        fun reConstructor(stationId: StationId) {

        }
    }
}