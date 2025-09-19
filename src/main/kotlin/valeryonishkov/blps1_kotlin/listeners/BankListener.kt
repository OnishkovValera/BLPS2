package valeryonishkov.blps1_kotlin.listeners

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


@Component
class BankListener(
    private val restTemplate: RestTemplate
) {

    @JmsListener(destination = "advertisement.payed")
    fun receiveMessage(message: String) {
        println("========================== Receive message from bank =====================")
        val (advertisementId, status) = parseMessage(message)
        val payedAdvertisementId: Long = advertisementId
        sendMessage(payedAdvertisementId)
    }

    fun parseMessage(message: String): Pair<Long, String> {
        val data: List<String> = message.split(":")
        return Pair(data[0].toLong(), data[1])
    }

    fun sendMessage(advertisementId: Long) {
        val url = "http://localhost:8080/engine-rest/message"
        val body = getObjectMap(advertisementId)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request: HttpEntity<MutableMap<String?, Any?>> = HttpEntity(body, headers)
        restTemplate.postForEntity(url, request, String::class.java)
    }

    private fun getObjectMap(advertisementId: Long): MutableMap<String?, Any?> {
        val body: MutableMap<String?, Any?> = HashMap()
        body.put("messageName", "GetPaymentCompleteMessage")
        body.put("processVariables", getCorrelation(advertisementId))
        body.put("correlationKeys", getCorrelation(advertisementId))
        return body
    }

    private fun getCorrelation(advertisementId: Long): MutableMap<String?, Any?> {
        val correlationKeyValue: MutableMap<String?, Any?> = HashMap()
        correlationKeyValue.put("value", advertisementId)
        correlationKeyValue.put("type", "Long")
        val correlationKeys: MutableMap<String?, Any?> = HashMap()
        correlationKeys.put("advertisementId", correlationKeyValue)
        return correlationKeys
    }
}