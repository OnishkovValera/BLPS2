package valeryonishkov.blps1_kotlin.component

import org.springframework.stereotype.Component
import valeryonishkov.blps1_kotlin.model.enums.PropertyType

@Component
class PriceCalculator {

    //TODO: доделать ценообразование
    fun calculatePrice(paidPromotional: Boolean, userId: Long, type: PropertyType) = if (paidPromotional) 100.0 else 0.0
}