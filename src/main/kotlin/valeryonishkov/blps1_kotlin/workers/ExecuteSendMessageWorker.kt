package valeryonishkov.blps1_kotlin.workers

import jakarta.annotation.PostConstruct
import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.service.AdvertisementService

@Component
class ExecuteSendMessageWorker(
    private val advertisementService: AdvertisementService,
    private val client: ExternalTaskClient,
) {

    @PostConstruct
    fun subscribe() {
        client.subscribe("send-advertisement")
            .lockDuration(1000)
            .handler(::executeSendMessage)
            .open()
    }


    private fun executeSendMessage(externalTask: ExternalTask, externalTaskService: ExternalTaskService) {
        println("========================== Send message to user =====================")
        advertisementService.sendAdvertisementMessage(
            externalTask.getVariable("advertisementId"),
            externalTask.getVariable("bankAccountNumber")
        )
        externalTaskService.complete(externalTask)
    }


}