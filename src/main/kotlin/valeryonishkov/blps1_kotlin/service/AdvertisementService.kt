package valeryonishkov.blps1_kotlin.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import valeryonishkov.blps1_kotlin.component.JmsTemplateWrapper
import valeryonishkov.blps1_kotlin.component.PriceCalculator
import valeryonishkov.blps1_kotlin.model.dto.AdvertisementDto
import valeryonishkov.blps1_kotlin.model.dto.toEntity
import valeryonishkov.blps1_kotlin.model.entity.User
import valeryonishkov.blps1_kotlin.model.entity.toDto
import valeryonishkov.blps1_kotlin.model.enums.AdvertisementStatus
import valeryonishkov.blps1_kotlin.repository.AdvertisementRepository
import valeryonishkov.blps1_kotlin.repository.UserRepository
import java.time.LocalDateTime

@Service
class AdvertisementService(
    private val advertisementRepository: AdvertisementRepository,
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val priceCalculator: PriceCalculator,
    private val jmsTemplateWrapper: JmsTemplateWrapper,
    private val emailService: EmailService,
) {
    fun createNewAdvertisement(advertisementDto: AdvertisementDto): AdvertisementDto? {
        val user = userService.getUserFromSecurityContext()
        val price = priceCalculator.calculatePrice(
            advertisementDto.paidPromotional,
            user!!.id as Long,
            advertisementDto.apartmentDescription.type
        )
        val advertisement = advertisementDto.toEntity(user, price)
        return advertisementRepository.save(advertisement).toDto()
    }

    @Transactional(rollbackFor = [Exception::class, RuntimeException::class])
    fun confirmAdvertisement(id: Long, bankAccountNumber: Long, status: Boolean) {
        var advertisementOnConfirmation = advertisementRepository.findById(id).orElseThrow()
        if (status) {
            if (advertisementOnConfirmation.price == 0.0) {
                advertisementOnConfirmation.advertisementStatus = AdvertisementStatus.PUBLISHED
                advertisementRepository.save(advertisementOnConfirmation)
            } else {
                advertisementOnConfirmation.advertisementStatus = AdvertisementStatus.ON_PAYMENT
                advertisementRepository.save(advertisementOnConfirmation)
                jmsTemplateWrapper.sendBill(
                    advertisementOnConfirmation.price,
                    bankAccountNumber,
                    advertisementOnConfirmation.id
                )
            }
        } else {
            advertisementOnConfirmation.advertisementStatus = AdvertisementStatus.ARCHIVED
            advertisementRepository.save(advertisementOnConfirmation)
        }
    }

    fun getAdvertisements(): MutableList<AdvertisementDto> {
        val user = userService.getUserFromSecurityContext() ?: throw RuntimeException("User is not authorized")
        return user.advertisements.map { it.toDto() }.toMutableList()

    }

    fun confirmPaidAdvertisement(payedAdvertisementId: Long) {
        val paidAdvertisement = advertisementRepository.findById(payedAdvertisementId).orElseThrow()
        paidAdvertisement.advertisementStatus = AdvertisementStatus.PUBLISHED
        println(paidAdvertisement.user.email)
        println("====================")

        emailService.sendEmail("Payment confirmation", paidAdvertisement.user.email)
    }

    @Transactional
    fun deleteByAdvertisementStatusAndTimeBefore(): Long {
        return advertisementRepository.deleteByAdvertisementStatusAndTimeBefore(AdvertisementStatus.ARCHIVED, LocalDateTime.now().minusYears(1))

    }
}