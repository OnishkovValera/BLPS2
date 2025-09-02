package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class ApartmentDescription(
    @field:[Id GeneratedValue(strategy = GenerationType.IDENTITY)]
    var id: Long,
    var numberOfRooms: Int,
    var area: Double,
    var floor: Int,
    var totalFloors: Int,
    var hasBalcony: Boolean,
    var descriptionText: String,
    @field:OneToOne(mappedBy = "apartmentDescription")
    var Advertisement: Advertisement

)