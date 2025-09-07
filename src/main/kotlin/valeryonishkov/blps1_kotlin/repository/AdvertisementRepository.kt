package valeryonishkov.blps1_kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import valeryonishkov.blps1_kotlin.model.entity.Advertisement

@Repository
interface AdvertisementRepository : JpaRepository<Advertisement, Long?>