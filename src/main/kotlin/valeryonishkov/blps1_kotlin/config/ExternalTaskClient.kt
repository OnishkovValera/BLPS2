package valeryonishkov.blps1_kotlin.config

import org.camunda.bpm.client.ExternalTaskClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExternalTaskClient {
    @Bean("externalTaskCamundaClient")
    fun externalTaskClient(): ExternalTaskClient? {
        return ExternalTaskClient.create()
            .baseUrl("http://localhost:8080/engine-rest")
            .asyncResponseTimeout(10000)
            .build()
    }
}