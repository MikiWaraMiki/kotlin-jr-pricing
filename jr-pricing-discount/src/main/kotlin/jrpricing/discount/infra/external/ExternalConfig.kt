package jrpricing.discount.infra.external

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ExternalConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}