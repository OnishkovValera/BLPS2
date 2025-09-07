package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.*
import valeryonishkov.blps1_kotlin.model.dto.AdvertisementDto
import valeryonishkov.blps1_kotlin.model.enums.AdvertisementStatus

@Entity
class Advertisement(
    @field:[Id GeneratedValue(strategy = GenerationType.IDENTITY)] var id: Long?,
    var email: String,
    var paidPromotional: Boolean,
    var advertisementStatus: AdvertisementStatus,
    var price: Double,
    @field:OneToOne(cascade = [CascadeType.ALL]) var address: Address,
    @field:OneToOne(cascade = [CascadeType.ALL]) var apartmentDescription: ApartmentDescription,
    @field:[ManyToOne(cascade = [CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH]) JoinColumn(name = "user_id")] var user: User?,
)

fun Advertisement.toDto(): AdvertisementDto {
    return AdvertisementDto(
        id = this.id,
        email = this.email,
        paidPromotional = this.paidPromotional,
        price = this.price,
        address = this.address.toDto(),
        apartmentDescription = this.apartmentDescription.toDto(),
        userId = this.user?.id ?: 0)
}