package formix

import kotlinx.serialization.Serializable

@Serializable
data class Reference(
    val uid: String,
    val field: String,
    val readonly: Boolean,
    val deleted: Boolean,
)