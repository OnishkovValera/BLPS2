package valeryonishkov.blps1_kotlin.model.entity

import jakarta.persistence.*
import valeryonishkov.blps1_kotlin.model.enums.Role

@Entity
@Table(name = "users")
class User(
    @field:[Id GeneratedValue(strategy = GenerationType.IDENTITY)] var id: Long?,
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var phoneNumber: String,
    var role: Role,
    @field:OneToMany(mappedBy = "user") var advertisements: MutableList<Advertisement>)
