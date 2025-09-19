package valeryonishkov.blps1_kotlin.workers

import jakarta.annotation.PostConstruct
import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.service.AdvertisementService

@Component
class ConfirmAdvertisementWorker(
    private val client: ExternalTaskClient,
    private val advertisementService: AdvertisementService,
) {

    @PostConstruct
    fun subscribe() {
        client.subscribe("publicate-advertisement")
            .lockDuration(1000)
            .handler(::execute)
            .open()
    }

    private fun execute(externalTask: ExternalTask, externalTaskService: ExternalTaskService) {
        val id = externalTask.getVariable<Long>("advertisementId")
        advertisementService.confirmFreeAdvertisement(id)
        externalTaskService.complete(externalTask)
    }
}