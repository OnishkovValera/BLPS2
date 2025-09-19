package valeryonishkov.blps1_kotlin.workers

import jakarta.annotation.PostConstruct
import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.service.EmailService

@Component
class SendMessageToUserWorker (
    private val client: ExternalTaskClient,
    private val emailService: EmailService
) {

    @PostConstruct
    fun subscribe() {
        client.subscribe("send-mail-message")
            .lockDuration(1000)
            .handler(::execute)
            .open()
    }

    fun execute(externalTask: ExternalTask, externalTaskService: ExternalTaskService) {
        emailService.sendEmail("Advertisement hello", "onishkovvaleryWork@gmail.com")
        externalTaskService.complete(externalTask)
    }
}
