package valeryonishkov.blps1_kotlin.config

import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.jaas.AuthorityGranter
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import valeryonishkov.blps1_kotlin.repository.UserRepository
import java.util.Map
import javax.security.auth.login.AppConfigurationEntry

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(private val userRepository: UserRepository) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable()}
            .cors(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .authenticationProvider(jaasAuthenticationProvider(configuration()))
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/advertisement", "/advertisement/**").permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling { exception ->
                exception.authenticationEntryPoint { _, response, _ ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                }
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun configuration(): InMemoryConfiguration {
        val configEntry = AppConfigurationEntry(
            JaasLoginModule::class.java.getName(),
            AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
            Map.of<String?, Any?>("userRepository", userRepository, "passwordEncoder", passwordEncoder())
        )
        val configurationEntries: Array<AppConfigurationEntry> = arrayOf(configEntry)
        return InMemoryConfiguration(
            Map.of<String?, Array<AppConfigurationEntry>?>(
                "SPRINGSECURITY",
                configurationEntries
            )
        )
    }

    fun jaasAuthenticationProvider(configuration: javax.security.auth.login.Configuration): AuthenticationProvider {
        val provider = DefaultJaasAuthenticationProvider()
        provider.setConfiguration(configuration)
        provider.setAuthorityGranters(arrayOf<AuthorityGranter>(JaasAuthorityGranter(userRepository)))
        provider.afterPropertiesSet()
        return provider
    }
}