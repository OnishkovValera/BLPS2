package valeryonishkov.blps1_kotlin.component

import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.repository.AdvertisementRepository
import valeryonishkov.blps1_kotlin.service.UserService

@Component
class SecurityHandler(
    private val userService: UserService,
    private val advertisementRepository: AdvertisementRepository
) {
    fun hasRightToConfirmThisAdvertisement(advertisementId: Long): Boolean{
        val user = userService.getUserFromSecurityContext() ?: throw RuntimeException("User is not authorized")
        val adv = advertisementRepository.findById(advertisementId).orElseThrow()
        return adv.user.id == user.id
    }
}