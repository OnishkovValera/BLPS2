package valeryonishkov.blps1_kotlin.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(private val javaMailSender: JavaMailSender) {
    fun sendEmail(text: String, to: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.from = "no-reply@cian.com"
        message.text = text
        message.subject = "Payment confirmation"
        println("Сообщение начало")
        javaMailSender.send(message)
        println("Сообщение отправлено============================")

    }
}