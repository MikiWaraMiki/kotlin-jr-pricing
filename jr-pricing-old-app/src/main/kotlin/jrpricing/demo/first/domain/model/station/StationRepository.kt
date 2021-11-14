package jrpricing.demo.first.domain.model.station

interface StationRepository {
    fun findByName(name: String): Station
}