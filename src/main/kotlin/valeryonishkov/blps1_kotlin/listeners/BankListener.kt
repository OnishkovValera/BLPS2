package valeryonishkov.blps1_kotlin.listeners

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.service.AdvertisementService

@Component
class BankListener(private val advertisementService: AdvertisementService) {

    @JmsListener(destination = "service.advertisement.payed")
    fun receiveMessage(message: String) {
        val payedAdvertisementId: Long = message.toLong()
        advertisementService.confirmPaidAdvertisement(payedAdvertisementId)
    }
}