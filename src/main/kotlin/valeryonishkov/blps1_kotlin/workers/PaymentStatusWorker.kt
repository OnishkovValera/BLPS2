package valeryonishkov.blps1_kotlin.workers

import jakarta.annotation.PostConstruct
import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.service.AdvertisementService

@Component
class PaymentStatusWorker(private val advertisementService: AdvertisementService,
    private val client: ExternalTaskClient
) {


    @PostConstruct
    fun subscribe() {
        client.subscribe("change-advertisement-to-draft")
            .lockDuration(1000)
            .handler(::toDraft)
            .open()
        client.subscribe("change-advertisement-to-active")
            .lockDuration(1000)
            .handler(::toActive)
            .open()
    }


    private fun toDraft(externalTask: ExternalTask, externalTaskService: ExternalTaskService) {
        advertisementService.confirmPaidAdvertisement(externalTask.getVariable("advertisementId"))
        externalTaskService.complete(externalTask)
    }


    private fun toActive(externalTask: ExternalTask, externalTaskService: ExternalTaskService) {
        advertisementService.unconfirmPaidAdvertisement(externalTask.getVariable("advertisementId"))
        externalTaskService.complete(externalTask)
    }
}

