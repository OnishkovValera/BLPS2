package valeryonishkov.blps1_kotlin.service

import org.springframework.stereotype.Service
import valeryonishkov.blps1_kotlin.component.PriceCalculator
import valeryonishkov.blps1_kotlin.model.dto.AdvertisementDto
import valeryonishkov.blps1_kotlin.model.dto.toEntity
import valeryonishkov.blps1_kotlin.model.entity.User
import valeryonishkov.blps1_kotlin.model.entity.toDto
import valeryonishkov.blps1_kotlin.model.enums.AdvertisementStatus
import valeryonishkov.blps1_kotlin.repository.AdvertisementRepository
import valeryonishkov.blps1_kotlin.repository.UserRepository

@Service
class AdvertisementService(
    private val advertisementRepository: AdvertisementRepository,
    private val userRepository: UserRepository,
    private val priceCalculator: PriceCalculator,
) {
    fun createNewAdvertisement(advertisementDto: AdvertisementDto): AdvertisementDto? {
        val user = userRepository.findById(advertisementDto.userId).orElseThrow()
        val price = priceCalculator.calculatePrice(advertisementDto.paidPromotional, advertisementDto.userId, advertisementDto.apartmentDescription.type)
        val advertisement = advertisementDto.toEntity(user, price)
        return advertisementRepository.save(advertisement).toDto()
    }

    fun confirmAdvertisement(id: Long, status: Boolean) {
        var advertisementOnConfirmation = advertisementRepository.findById(id).orElseThrow()
        advertisementOnConfirmation.advertisementStatus = if (status) AdvertisementStatus.CONFIRMED else AdvertisementStatus.ARCHIVED
        advertisementRepository.save(advertisementOnConfirmation)
    }

    fun getAdvertisements(userId: Long):MutableList<AdvertisementDto> {
        val user: User = userRepository.findById(userId).orElseThrow()
        return user.advertisements.map { it.toDto() }.toMutableList()

    }
}