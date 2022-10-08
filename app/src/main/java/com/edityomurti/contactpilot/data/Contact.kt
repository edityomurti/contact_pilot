package com.edityomurti.contactpilot.data

data class Contact(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var email: String?,
    var phone: String?,
): java.io.Serializable {
    companion object {
        val key = "CONTACT_KEY"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Contact) return false
        return this.id == other.id &&
        this.firstName == other.firstName &&
        this.lastName == other.lastName &&
        this.email == other.email &&
        this.phone == other.phone
    }
}