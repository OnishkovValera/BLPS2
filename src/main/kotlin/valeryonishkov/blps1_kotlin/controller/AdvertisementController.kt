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

    @PreAuthorize("@securityHandler.hasRightToConfirmThisAdvertisement(#id) && hasAuthority('CONFIRM_ADVERTISEMENT')")
    @PutMapping
    fun confirmAdvertisement(@RequestParam id: Long, @RequestParam bankAccountNumber: Long, @RequestParam status: Boolean): ResponseEntity<Unit> {
        advertisementService.confirmAdvertisement(id, bankAccountNumber, status)
        return ResponseEntity.ok().build()
    }

    @PreAuthorize("hasAuthority('CHECK_ADVERTISEMENT')")
    @GetMapping
    fun getAdvertisements(): ResponseEntity<MutableList<AdvertisementDto>> {
        val advertisementList = advertisementService.getAdvertisements()
        return ResponseEntity.ok(advertisementList)
    }

}
