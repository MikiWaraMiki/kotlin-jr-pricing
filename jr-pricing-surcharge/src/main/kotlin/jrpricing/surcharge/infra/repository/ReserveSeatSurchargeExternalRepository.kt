package jrpricing.surcharge.infra.repository

import jrpricing.surcharge.domain.shared.Amount
import jrpricing.surcharge.domain.shared.SeatType
import jrpricing.surcharge.domain.shared.TrainType
import jrpricing.surcharge.domain.shared.TripRoute
import jrpricing.surcharge.domain.surcharge.ReserveSeatSurcharge
import jrpricing.surcharge.domain.surcharge.ReserveSeatSurchargeRepository
import org.springframework.stereotype.Repository

@Repository
class ReserveSeatSurchargeExternalRepository: ReserveSeatSurchargeRepository {
    // TODO: 実装
    override fun findByTripRoute(tripRoute: TripRoute, trainType: TrainType): ReserveSeatSurcharge? {
        return ReserveSeatSurcharge(Amount.of(1000))
    }
}