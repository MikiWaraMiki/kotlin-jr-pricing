package jrpricing.fare

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FareApplication

fun main(args: Array<String>) {
    runApplication<FareApplication>(*args)
}