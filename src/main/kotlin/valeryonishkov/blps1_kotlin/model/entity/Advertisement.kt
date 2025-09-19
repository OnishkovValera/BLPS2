package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.*
import valeryonishkov.blps1_kotlin.model.dto.AdvertisementDto
import valeryonishkov.blps1_kotlin.model.enums.AdvertisementStatus
import java.time.LocalDateTime

@Entity
class Advertisement(
    @field:[Id GeneratedValue(strategy = GenerationType.IDENTITY)] var id: Long?,
    var email: String,
    @Column(name = "paid_promotional")
    var paidPromotional: Boolean,
    @Column(name = "advertisement_status")
    var advertisementStatus: AdvertisementStatus,
    var price: Double,
    var time: LocalDateTime = LocalDateTime.now(),
    @field:OneToOne(cascade = [CascadeType.ALL]) var address: Address,
    @field:[OneToOne(cascade = [CascadeType.ALL]) PrimaryKeyJoinColumn(name = "apartment_description_id")] var apartmentDescription: ApartmentDescription,
    @field:[ManyToOne(cascade = [CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH]) JoinColumn(name = "user_id")] var user: User,
)

fun Advertisement.toDto(): AdvertisementDto {
    return AdvertisementDto(
        id = this.id,
        email = this.email,
        paidPromotional = this.paidPromotional,
        price = this.price,
        address = this.address.toDto(),
        time = this.time,
        apartmentDescription = this.apartmentDescription.toDto()
    )
}