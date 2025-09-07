package valeryonishkov.blps1_kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import valeryonishkov.blps1_kotlin.model.entity.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}