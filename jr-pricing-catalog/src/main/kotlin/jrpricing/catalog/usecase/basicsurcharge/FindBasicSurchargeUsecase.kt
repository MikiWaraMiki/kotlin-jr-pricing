package jrpricing.catalog.usecase.basicsurcharge

import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.model.surcharge.BasicSurcharge
import jrpricing.catalog.domain.model.surcharge.BasicSurchargeRepository
import jrpricing.catalog.domain.model.surcharge.NozomiAdditionalChargeRepository
import jrpricing.catalog.domain.model.train.TrainType
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import org.springframework.stereotype.Service
import java.lang.Exception

/**
 * 指定された駅間の特急料金を返すユースケース
 */
@Service
class FindBasicSurchargeUsecase(
    private val routeRepository: RouteRepository,
    private val basicSurchargeRepository: BasicSurchargeRepository,
    private val nozomiAdditionalChargeRepository: NozomiAdditionalChargeRepository
) {
    fun execute(departureStationId: StationId, arrivalStationId: StationId, trainType: TrainType): BasicSurcharge {
        val route = routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId)
            ?: throw AssertionFailException("指定した駅間の経路は存在しません", ErrorCode.NOTFOUND_ASSERTION)

        // NOTE: 経路が登録されて料金が登録されていない場合は異常系とみなす
        val basicSurcharge = basicSurchargeRepository.findByRouteId(route.routeId)
            ?: throw Exception("指定した経路の特急料金は存在しません")

        if (!trainType.isNozomi()) return basicSurcharge

        val nozomiAdditionalCharge = nozomiAdditionalChargeRepository.findByRouteId(route.routeId)
            ?: throw Exception("指定した経路ののぞみ追加料金が存在しません")

        return basicSurcharge.addNozomiAdditionalCharge(nozomiAdditionalCharge)
    }
}