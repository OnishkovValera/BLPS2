package valeryonishkov.blps1_kotlin.component

import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@Component
class JmsTemplateWrapper(private val jmsTemplate: JmsTemplate) {
    companion object {
        const val BANK_QUEUE_NAME = "bank.bill"

    }

    fun sendBill(price: Double, bankAccountNumber: Long?, advertisementId: Long?) {
        jmsTemplate.convertAndSend(BANK_QUEUE_NAME, "$bankAccountNumber:$price:$advertisementId")
    }
}