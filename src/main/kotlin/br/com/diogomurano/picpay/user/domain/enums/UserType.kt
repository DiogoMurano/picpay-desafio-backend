package br.com.diogomurano.picpay.user.domain.enums

enum class UserType {

    RETAILER,
    COMMON;

    companion object {
        fun findByName(name: String) = values().find { it.name.equals(name, ignoreCase = true) }
    }

}
