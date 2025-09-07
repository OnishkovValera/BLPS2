package valeryonishkov.blps1_kotlin.model.enums

enum class Role(val privileges: Set<Privilege>) {
    USER(setOf(Privilege.CHECK_ADVERTISEMENT, Privilege.CONFIRM_ADVERTISEMENT, Privilege.CREATE_ADVERTISEMENT)),
}
