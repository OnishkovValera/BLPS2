package valeryonishkov.blps1_kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import valeryonishkov.blps1_kotlin.model.entity.Advertisement
import valeryonishkov.blps1_kotlin.model.enums.AdvertisementStatus
import java.time.LocalDateTime

@Repository
interface AdvertisementRepository : JpaRepository<Advertisement, Long?> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    fun deleteByAdvertisementStatusAndTimeBefore(status: AdvertisementStatus, time: LocalDateTime): Long
}