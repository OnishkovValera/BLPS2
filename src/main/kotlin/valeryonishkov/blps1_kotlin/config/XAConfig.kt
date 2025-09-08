package valeryonishkov.blps1_kotlin.config

import com.atomikos.jms.AtomikosConnectionFactoryBean
import com.atomikos.spring.AtomikosDataSourceBean
import jakarta.jms.ConnectionFactory
import jakarta.jms.XAConnectionFactory
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory
import org.postgresql.xa.PGXADataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.core.JmsTemplate
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource

@Configuration
@EnableJms
@EnableJpaRepositories(
    basePackages = ["valeryonishkov.blps1_kotlin.repository"],
    entityManagerFactoryRef = "jtaEntityManager"
)
class XaConfig {

    @Bean
    fun xaDataSource(): DataSource {
        val pgXa = PGXADataSource().apply {
            serverNames = arrayOf("localhost")
            portNumbers = intArrayOf(5432)
            databaseName = "Cian"
            user = "postgres"
            password = "admin"
        }

        return AtomikosDataSourceBean().apply {
            uniqueResourceName = "xa-postgres"
            xaDataSource = pgXa
            minPoolSize = 1
            maxPoolSize = 10
        }
    }

    @Bean
    fun jtaEntityManager(
        builder: EntityManagerFactoryBuilder,
        xaDataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val props = mapOf(
            "jakarta.persistence.transactionType" to "JTA",
            "hibernate.transaction.coordinator_class" to "jta",
            "hibernate.transaction.jta.platform" to "org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform"
        )

        return builder
            .dataSource(xaDataSource)
            .packages("valeryonishkov.blps1_kotlin.model.entity")
            .properties(props)
            .jta(true)
            .build()
    }

    @Bean(name = ["xacon"])
    fun artemisXaConnectionFactory(): XAConnectionFactory {
        return ActiveMQXAConnectionFactory("tcp://localhost:61616").apply {
            user = "admin"
            password = "admin"
        }
    }

    @Bean(name = ["jmsConXA"])
    @Primary
    fun jmsConnectionFactory(@Qualifier("xacon") xaCf: XAConnectionFactory): ConnectionFactory {
        return AtomikosConnectionFactoryBean().apply {
            uniqueResourceName = "xa-artemis"
            xaConnectionFactory = xaCf
            setPoolSize(5)
        }
    }

    @Bean
    fun jmsTemplate(@Qualifier("jmsConXA") cf: ConnectionFactory): JmsTemplate {
        return JmsTemplate(cf).apply {
            isSessionTransacted = true
        }
    }
}