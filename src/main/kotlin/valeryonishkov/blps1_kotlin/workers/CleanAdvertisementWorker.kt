package valeryonishkov.blps1_kotlin.workers

import jakarta.annotation.PostConstruct
import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.service.AdvertisementService

@Component
class CleanAdvertisementWorker(
    private val advertisementService: AdvertisementService,
    private val client: ExternalTaskClient,

    ) {

    @PostConstruct
    fun subscribe() {
        client.subscribe("publicate-advertisement")
            .lockDuration(1000)
            .handler(::execute)
            .open()
    }
    
    fun execute(externalTask: ExternalTask, externalTaskService: ExternalTaskService){
        advertisementService.deleteByAdvertisementStatusAndTimeBefore()
        externalTaskService.complete(externalTask)
    }
}