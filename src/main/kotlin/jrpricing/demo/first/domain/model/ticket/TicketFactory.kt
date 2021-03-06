package jrpricing.demo.first.domain.model.ticket

import jrpricing.demo.first.domain.model.route.Route
import jrpricing.demo.first.domain.model.station.Station
import jrpricing.demo.first.domain.model.train.SeatType
import jrpricing.demo.first.domain.model.train.TrainType

/**
 * 要求されたTicketオブジェクトを生成する
 */
interface TicketFactory {
    fun generate(
        departureStationLabel: String,
        arrivalStationLabel: String,
        departureDate: String,
        ticketTypeLabel: String,
        trainTypeLabel: String,
        seatTypeLabel: String,
        isChild: Boolean
    ): Ticket {
        // NOTE: Enum定義から生成するのでデフォルト実装
        // DBに格納し始めてから、インフラ層に実装クラスを書く
        val departureStation = Station.fromLabel(departureStationLabel)
        val arrivalStation = Station.fromLabel(arrivalStationLabel)

        val departureDate = DepartureDate.of(departureDate)

        val route = Route(departureStation, arrivalStation)

        val tripType = TripType.of(ticketTypeLabel)
        val trainType = TrainType.fromLabel(trainTypeLabel)
        val seatType = SeatType.fromLabel(seatTypeLabel)


        return Ticket(
            route,
            departureDate,
            tripType,
            trainType,
            seatType,
            isChild
        )
    }
}