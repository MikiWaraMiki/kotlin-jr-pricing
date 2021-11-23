package jrpricing.surcharge.usecase.surcharge

import jrpricing.surcharge.domain.shared.DepartureMonthDay
import jrpricing.surcharge.domain.shared.SeatType
import jrpricing.surcharge.domain.shared.TrainType
import jrpricing.surcharge.domain.shared.TripRoute
import jrpricing.surcharge.domain.surcharge.ReserveSeatSurchargeRepository
import jrpricing.surcharge.domain.surcharge.Surcharge
import jrpricing.surcharge.domain.surcharge.SurchargeCalculator
import jrpricing.surcharge.usecase.exception.AssertionFailException
import jrpricing.surcharge.usecase.exception.ErrorCode

/**
 * 特急料金検索ユースケース
 */
class FindSurchargeUsecase(
    private val reserveSeatSurchargeRepository: ReserveSeatSurchargeRepository
) {
    private val surchargeCalculator = SurchargeCalculator()

    fun execute(
        departureStationId: String,
        arrivalStationId: String,
        seatType: SeatType,
        trainType: TrainType,
        departureMonthDay: DepartureMonthDay
    ): Surcharge {
        val tripRoute = TripRoute(departureStationId, arrivalStationId)

        val reserveSeatSurcharge = reserveSeatSurchargeRepository.findByTripRoute(tripRoute, trainType)
            ?: throw AssertionFailException("存在しない経路です", ErrorCode.NOTFOUND_ASSERTION)

        return surchargeCalculator.calc(reserveSeatSurcharge, departureMonthDay, seatType)
    }
}