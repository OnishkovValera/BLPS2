package valeryonishkov.blps1_kotlin.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.repository.UserRepository

@Component
class UserService(private val userRepository: UserRepository) {
    fun getUserFromSecurityContext() = userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name)
}