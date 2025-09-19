package valeryonishkov.blps1_kotlin.workers

import jakarta.annotation.PostConstruct
import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskService
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import valeryonishkov.blps1_kotlin.model.dto.AddressDto
import valeryonishkov.blps1_kotlin.model.dto.AdvertisementDto
import valeryonishkov.blps1_kotlin.model.dto.ApartmentDescriptionDto
import valeryonishkov.blps1_kotlin.model.enums.PropertyType
import valeryonishkov.blps1_kotlin.service.AdvertisementService
import java.time.LocalDateTime

@Component
class CreateAdvertisementWorker(
    private val client: ExternalTaskClient,
    private val advertisementService: AdvertisementService,
    private val restTemplate: RestTemplate
) {

    @PostConstruct
    fun subscribe() {
        client.subscribe("create-advertisement")
            .lockDuration(1000)
            .handler(::execute)
            .open()
    }


    private fun execute(externalTask: ExternalTask, externalTaskService: ExternalTaskService) {
        val createdAdvertisement = advertisementService.createNewAdvertisement(getDataFromExternalTaskAndCreateAdvertisementDto(externalTask))
        val variables: VariableMap = Variables.createVariables()
            .putValue("advertisementId", Variables.longValue(createdAdvertisement?.id))
            .putValue("price", Variables.longValue(createdAdvertisement?.price?.toLong()))
        externalTaskService.complete(externalTask, variables)
    }

    private fun getDataFromExternalTaskAndCreateAdvertisementDto(externalTask: ExternalTask): AdvertisementDto {
        return AdvertisementDto(
            id = null,
            email = "onishkovvalerywork@gmail.com", //externalTask.getVariable("initiator") as String,
            paidPromotional = externalTask.getVariable("paidPromotional"),
            price = null,
            address = AddressDto(
                id = null,
                country = externalTask.getVariable("country"),
                city = externalTask.getVariable("city"),
                street = externalTask.getVariable("street"),
                houseNumber = externalTask.getVariable("houseNumber"),
                postalCode = externalTask.getVariable("postalCode"),
                apartmentNumber = "1" //externalTask.getVariable("apartmentNumber") as String,
            ),
            time = LocalDateTime.now(),
            apartmentDescription = ApartmentDescriptionDto(
                id = null,
                area = externalTask.getVariable("area"),
                type = PropertyType.valueOf(externalTask.getVariable("propertyType")),
                floor = externalTask.getVariable("floor"),
                numberOfRooms = externalTask.getVariable("numberOfRooms"),
                totalFloors = externalTask.getVariable("totalFloors"),
                hasBalcony = externalTask.getVariable("hasBalcony"),
                descriptionText = externalTask.getVariable("descriptionText"),
            )
        )
    }

}