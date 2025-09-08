package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import valeryonishkov.blps1_kotlin.model.dto.ApartmentDescriptionDto
import valeryonishkov.blps1_kotlin.model.enums.PropertyType

@Entity
@Table(name = "apartment_description")
class ApartmentDescription(
    @field:[Id GeneratedValue(strategy = GenerationType.IDENTITY)] var id: Long?,
    @Column(name = "number_of_rooms")
    var numberOfRooms: Int,
    var area: Double,
    var type: PropertyType,
    var floor: Int,
    @Column(name = "total_floors")
    var totalFloors: Int,
    @Column(name = "has_balcony")
    var hasBalcony: Boolean,
    @Column(name = "description_text")
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