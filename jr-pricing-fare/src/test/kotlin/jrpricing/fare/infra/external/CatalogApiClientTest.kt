package jrpricing.fare.infra.external

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate

/**
 * TODO: テストの実装
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest
class CatalogApiClientTest(
    @Autowired private val restTemplate: RestTemplate
) {

}