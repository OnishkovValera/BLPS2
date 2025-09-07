package valeryonishkov.blps1_kotlin.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import valeryonishkov.blps1_kotlin.model.dto.AdvertisementDto
import valeryonishkov.blps1_kotlin.service.AdvertisementService

@RestController
@RequestMapping("/advertisement")
class AdvertisementController(private val advertisementService: AdvertisementService) {

    @PreAuthorize("hasAuthority('CREATE_ADVERTISEMENT')")
    @PostMapping
    fun createAdvertisement(@RequestBody @Validated advertisementDto: AdvertisementDto): ResponseEntity<AdvertisementDto> {
        return ResponseEntity.ok(advertisementService.createNewAdvertisement(advertisementDto))
    }

    @PreAuthorize("hasAuthority('CONFIRM_ADVERTISEMENT')")
    @PutMapping
    fun confirmAdvertisement(@RequestParam id: Long, @RequestParam status: Boolean): ResponseEntity<Unit> {
        advertisementService.confirmAdvertisement(id, status)
        return ResponseEntity.ok().build()
    }

    @PreAuthorize("hasAuthority('CHECK_ADVERTISEMENT')")
    @GetMapping
    fun getAdvertisements(@RequestParam userId: Long): ResponseEntity<MutableList<AdvertisementDto>> {
        val principal: String = SecurityContextHolder.getContext().authentication.principal as String
        println(principal)
        val advertisementList = advertisementService.getAdvertisements(userId)
        return ResponseEntity.ok(advertisementList)
    }

}
