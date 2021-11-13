package jrpricing.catalog.domain.model.station

import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.exception.ErrorCode

/**
 * 駅名クラス
 */
class StationName(
    val value: String
){
    init {
        if (value == "") {
            throw DomainException("駅名が入力されていません", ErrorCode.INVALID_INPUT)
        }

        if (value.length > MAX_STATION_NAME_LENGTH) {
            throw DomainException("駅名は50文字以内で入力してください", ErrorCode.INVALID_INPUT)
        }
    }

    companion object {
        private const val MAX_STATION_NAME_LENGTH = 50
    }
}