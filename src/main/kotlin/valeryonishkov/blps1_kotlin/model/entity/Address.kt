package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class Address(
    @field:[Id GeneratedValue(GenerationType.IDENTITY)] var id: Long?,
    var country: String,
    var city: String,
    var street: String,
    var houseNumber: String,
    var apartmentNumber: String,
    var postalCode: String,
    @field:OneToOne(mappedBy = "address") var advertisement: Advertisement,
)