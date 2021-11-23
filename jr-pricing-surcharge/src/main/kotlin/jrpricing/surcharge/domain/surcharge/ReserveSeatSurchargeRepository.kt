package jrpricing.surcharge.domain.surcharge

import jrpricing.surcharge.domain.shared.SeatType
import jrpricing.surcharge.domain.shared.TrainType
import jrpricing.surcharge.domain.shared.TripRoute

interface ReserveSeatSurchargeRepository {
    fun findByTripRoute(tripRoute: TripRoute, trainType: TrainType): ReserveSeatSurcharge?
}