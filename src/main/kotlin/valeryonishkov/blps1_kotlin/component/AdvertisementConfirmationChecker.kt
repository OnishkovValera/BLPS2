package valeryonishkov.blps1_kotlin.component

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.model.enums.AdvertisementStatus
import valeryonishkov.blps1_kotlin.repository.AdvertisementRepository
import java.time.LocalDateTime

@Component
class AdvertisementConfirmationChecker(
    private val advertisementRepository: AdvertisementRepository
) {
    @Scheduled(fixedRate = 60000) // Check every minute
    fun checkAdvertisementStatus() {
        val tenMinutesAgo = LocalDateTime.now().minusMinutes(10)
        val expiredAds = advertisementRepository.findAll()
            .filter { it.time.isBefore(tenMinutesAgo) && it.advertisementStatus == AdvertisementStatus.CREATED }

        expiredAds.forEach { ad ->
            ad.advertisementStatus = AdvertisementStatus.ARCHIVED
            advertisementRepository.save(ad)
        }
    }
}