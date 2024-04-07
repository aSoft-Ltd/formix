package formix

import kollections.MutableList
import kotlinx.serialization.Serializable

@Serializable
data class Page(
    val uid: String,
    val name: String,
    val references: MutableList<Reference>,
    val background: String? = null,
    val readonly: Boolean = false,
    val deleted: Boolean = false,
)