package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import valeryonishkov.blps1_kotlin.model.dto.AddressDto

@Entity
class Address(
    @field:[Id GeneratedValue(strategy = GenerationType.IDENTITY)] var id: Long?,
    var country: String,
    var city: String,
    var street: String,
    @Column(name = "house_number")
    var houseNumber: String,
    @Column(name = "apartment_number")
    var apartmentNumber: String,
    @Column(name = "postal_code")
    var postalCode: String,
)

fun Address.toDto() = AddressDto(
    id = this.id,
    country = this.country,
    city = this.city,
    street = this.street,
    houseNumber =  this.houseNumber,
    apartmentNumber = this.apartmentNumber,
    postalCode = this.postalCode)