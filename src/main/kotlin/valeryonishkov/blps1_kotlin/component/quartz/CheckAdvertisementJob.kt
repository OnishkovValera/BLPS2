package valeryonishkov.blps1_kotlin.component.quartz

import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.service.AdvertisementService

@Component
@DisallowConcurrentExecution
class CheckAdvertisementJob(private val advertisementService: AdvertisementService): QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        val count = advertisementService.deleteByAdvertisementStatusAndTimeBefore()
        println("========================== Deleted $count advertisements =====================")
    }
}