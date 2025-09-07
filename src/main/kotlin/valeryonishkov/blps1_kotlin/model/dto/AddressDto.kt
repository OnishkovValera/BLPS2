package valeryonishkov.blps1_kotlin.model.dto

import jakarta.validation.constraints.NotBlank
import valeryonishkov.blps1_kotlin.model.entity.Address
import valeryonishkov.blps1_kotlin.model.entity.User

data class AddressDto(
    val id: Long?,
    @NotBlank val country: String,
    @NotBlank val city: String,
    @NotBlank val street: String,
    @NotBlank val houseNumber: String,
    @NotBlank val apartmentNumber: String,
    @NotBlank val postalCode: String,
)
fun AddressDto.toEntity(user: User?) = Address(
    id = null,
    country = this.country,
    city = this.city,
    street = this.street,
    houseNumber = this.houseNumber,
    apartmentNumber = this.apartmentNumber,
    postalCode = this.postalCode,
)

fun AddressDto.toEntity() = Address(
    id = this.id,
    country = this.country,
    city = this.city,
    street = this.street,
    houseNumber = this.houseNumber,
    apartmentNumber = this.apartmentNumber,
    postalCode = this.postalCode,
)
