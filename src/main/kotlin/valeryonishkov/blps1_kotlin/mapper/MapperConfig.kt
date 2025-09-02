package valeryonishkov.blps1_kotlin.mapper

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapperConfig {

    @Bean
    fun GenericMapper(): GenericMapper = GenericMapper()

}