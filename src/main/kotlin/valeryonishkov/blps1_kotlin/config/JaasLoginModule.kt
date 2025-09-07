package valeryonishkov.blps1_kotlin.config

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.client.HttpClientErrorException
import valeryonishkov.blps1_kotlin.model.entity.User
import valeryonishkov.blps1_kotlin.repository.UserRepository
import java.io.IOException
import java.nio.file.attribute.UserPrincipal
import java.security.Principal
import javax.security.auth.Subject
import javax.security.auth.callback.*
import javax.security.auth.spi.LoginModule

class JaasLoginModule : LoginModule {
    private var userRepository: UserRepository? = null
    private var passwordEncoder: PasswordEncoder? = null
    private var login: String? = null
    private lateinit var subject: Subject
    private var loginSucceeded = false
    private var callbackHandler: CallbackHandler? = null

    override fun initialize(
        subject: Subject,
        callbackHandler: CallbackHandler,
        sharedState: Map<String, *>,
        options: Map<String, *>
    ) {
        this.subject = subject
        this.callbackHandler = callbackHandler
        this.userRepository = options["userRepository"] as? UserRepository
        this.passwordEncoder = options["passwordEncoder"] as? PasswordEncoder
        
        // Проверяем, что все зависимости инициализированы
        if (this.userRepository == null) {
            throw IllegalStateException("UserRepository is not initialized")
        }
        if (this.passwordEncoder == null) {
            throw IllegalStateException("PasswordEncoder is not initialized")
        }
        if (this.callbackHandler == null) {
            throw IllegalStateException("CallbackHandler is not initialized")
        }
    }

    override fun login(): Boolean {
        val nameCallback = NameCallback("login: ")
        val passwordCallback = PasswordCallback("password: ", false)
        
        try {
            callbackHandler?.handle(arrayOf<Callback>(nameCallback, passwordCallback))
                ?: throw IllegalStateException("CallbackHandler is null")
        } catch (e: IOException) {
            throw RuntimeException("Failed to handle callbacks", e)
        } catch (e: UnsupportedCallbackException) {
            throw RuntimeException("Unsupported callback", e)
        }
        
        login = nameCallback.name
        
        // Исправление: безопасное получение пароля
        val password = String(passwordCallback.password)

        if (login.isNullOrBlank()) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Login is null or empty")
        }
        
        if (password.isEmpty()) {
            loginSucceeded = false
            return false
        }
        
        val user: User? = userRepository?.findByEmail(login!!)
        loginSucceeded = user != null && passwordEncoder?.matches(password, user.password) == true
        
        return loginSucceeded
    }

    override fun commit(): Boolean {
        if (!loginSucceeded) return false
        if (login.isNullOrBlank()) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Login is null or empty")
        }
        val principal: Principal = UserPrincipal { login }
        subject.getPrincipals().add(principal)
        return true
    }

    override fun abort() = false

    override fun logout(): Boolean {
        subject.getPrincipals().removeIf { principal: Principal? -> principal is UserPrincipal }
        loginSucceeded = false
        return true
    }
}