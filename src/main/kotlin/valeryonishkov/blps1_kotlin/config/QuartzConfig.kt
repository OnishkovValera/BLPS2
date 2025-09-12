package valeryonishkov.blps1_kotlin.config

import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import valeryonishkov.blps1_kotlin.component.quartz.CheckAdvertisementJob
import kotlin.jvm.java

@Configuration
class QuartzConfig {
    @Bean
    fun checkAdsJobDetail(): JobDetail =
        JobBuilder.newJob(CheckAdvertisementJob::class.java)
            .withIdentity("checkAdsJob")
            .storeDurably()
            .build()

    @Bean
    fun checkAdsTrigger(jobDetail: JobDetail): Trigger =
        TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("checkAdsTrigger")
            .withSchedule(
                CronScheduleBuilder.cronSchedule("0 30 3 * * ?")
            )
            .build()

}