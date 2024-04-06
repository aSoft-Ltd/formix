package formix

import kotlinx.serialization.Serializable

@Serializable
data class Reference(
    val uid: String,
    val field: String,
    val option: String? = null,
    val readonly: Boolean = false,
    val deleted: Boolean = false,
)