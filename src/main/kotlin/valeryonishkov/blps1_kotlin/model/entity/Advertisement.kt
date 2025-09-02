package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.*
import valeryonishkov.blps1_kotlin.model.enums.AdvertisementStatus

@Entity
class Advertisement(
    @field:[Id GeneratedValue(strategy = GenerationType.IDENTITY)] var id: Long,
    var email: String,
    var paidPromotional: Boolean,
    var status: AdvertisementStatus,
    @field:OneToOne(cascade = [CascadeType.ALL])
    var address: Address,
    @field:OneToOne(cascade = [CascadeType.ALL])
    var apartmentDescription: ApartmentDescription
)
