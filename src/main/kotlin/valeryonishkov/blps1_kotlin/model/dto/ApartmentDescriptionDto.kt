package valeryonishkov.blps1_kotlin.model.dto

import jakarta.validation.constraints.NotBlank
import valeryonishkov.blps1_kotlin.model.entity.ApartmentDescription
import valeryonishkov.blps1_kotlin.model.enums.PropertyType

data class ApartmentDescriptionDto(
    val id: Long?,
    val numberOfRooms: Int,
    val type: PropertyType,
    val area: Double,
    val floor: Int,
    val totalFloors: Int,
    val hasBalcony: Boolean,
    @field:NotBlank val descriptionText: String,
)
fun ApartmentDescriptionDto.toEntity() = ApartmentDescription(
    id = this.id ,
    numberOfRooms = this.numberOfRooms,
    area = this.area,
    floor = this.floor,
    totalFloors = this.totalFloors,
    hasBalcony = this.hasBalcony,
    type = this.type,
    descriptionText = this.descriptionText,
)
