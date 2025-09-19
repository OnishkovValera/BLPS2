package valeryonishkov.blps1_kotlin.workers

import jakarta.annotation.PostConstruct
import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.service.AdvertisementService


@Component
class TransactionWorker(
    private val advertisementService: AdvertisementService,
    private val client: ExternalTaskClient,
) {

    @PostConstruct
    fun subscribe() {
        client.subscribe("change-on-payment-status")
            .lockDuration(1000)
            .handler(::executeChangeStatus)
            .open()
    }


    private fun executeChangeStatus(externalTask: ExternalTask, externalTaskService: ExternalTaskService) {
        advertisementService.changeAdvertisementStatus(externalTask.getVariable("advertisementId"))
        externalTaskService.complete(externalTask)
    }




}