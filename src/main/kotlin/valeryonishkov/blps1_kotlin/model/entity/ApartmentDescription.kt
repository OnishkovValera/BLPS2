package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import valeryonishkov.blps1_kotlin.model.dto.ApartmentDescriptionDto
import valeryonishkov.blps1_kotlin.model.enums.PropertyType

@Entity
class ApartmentDescription(
    @field:[Id GeneratedValue(strategy = GenerationType.IDENTITY)] var id: Long?,
    var numberOfRooms: Int,
    var area: Double,
    var type: PropertyType,
    var floor: Int,
    var totalFloors: Int,
    var hasBalcony: Boolean,
    var descriptionText: String,
)

fun ApartmentDescription.toDto() = ApartmentDescriptionDto(
    descriptionText = this.descriptionText,
    id = this.id,
    numberOfRooms = this.numberOfRooms,
    area = this.area,
    floor = this.floor,
    totalFloors = this.totalFloors,
    hasBalcony = this.hasBalcony,
    type = this.type,
)