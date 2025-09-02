package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    @field:[Id GeneratedValue(GenerationType.IDENTITY)] var id: Long?,
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var phoneNumber: String,
    @field:OneToMany(mappedBy = "user") var advertisements: MutableList<Advertisement>)