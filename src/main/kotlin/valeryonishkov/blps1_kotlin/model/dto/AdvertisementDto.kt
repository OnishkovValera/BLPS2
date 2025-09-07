package valeryonishkov.blps1_kotlin.model.dto

import jakarta.validation.constraints.Email
import valeryonishkov.blps1_kotlin.model.entity.Advertisement
import valeryonishkov.blps1_kotlin.model.entity.User
import valeryonishkov.blps1_kotlin.model.enums.AdvertisementStatus

data class AdvertisementDto(
    var id: Long?,
    @field:Email var email: String,
    var paidPromotional: Boolean,
    var address: AddressDto,
    var price: Double?,
    var apartmentDescription: ApartmentDescriptionDto,
    var userId: Long,
)



fun AdvertisementDto.toEntity(user: User, calculatedPrice: Double) = Advertisement(
    id = this.id,
    email = this.email,
    paidPromotional = this.paidPromotional,
    address = this.address.toEntity(),
    apartmentDescription = this.apartmentDescription.toEntity(),
    user = user,
    advertisementStatus = AdvertisementStatus.CREATED,
    price = calculatedPrice
)
