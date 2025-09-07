package valeryonishkov.blps1_kotlin.component

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AdvertisementConfirmationChecker {
    @Scheduled
    fun checkAdvertisementStatus() {

    }
}