package formix

import kotlinx.serialization.Serializable

@Serializable
sealed interface Field {
    val uid: String
    val name: String
    val label: String
    val readonly: Boolean
    val deleted: Boolean
}