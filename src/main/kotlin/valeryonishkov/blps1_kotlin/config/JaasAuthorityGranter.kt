package valeryonishkov.blps1_kotlin.config

import org.springframework.security.authentication.jaas.AuthorityGranter
import valeryonishkov.blps1_kotlin.model.entity.User
import valeryonishkov.blps1_kotlin.repository.UserRepository
import java.security.Principal


class JaasAuthorityGranter(private val userRepository: UserRepository) : AuthorityGranter {

    override fun grant(principal: Principal): Set<String> {
        val user: User? = userRepository.findByEmail(principal.name)
        val privileges: Set<String> = user?.role?.privileges?.map { it.name }?.toSet() ?: setOf()
        return privileges
    }


}
