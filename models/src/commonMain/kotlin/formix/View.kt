package formix

import kollections.MutableList
import kotlinx.serialization.Serializable

@Serializable
data class View(
    val uid: String,
    val label: String,
    val target: Target,
    val pages: MutableList<Page>,
    val readonly: Boolean = false,
    val deleted: Boolean = false,
)