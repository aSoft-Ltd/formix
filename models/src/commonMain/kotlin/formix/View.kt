package formix

data class View(
    val uid: String,
    val label: String,
    val target: Target,
    val pages: List<Page>,
    val readonly: Boolean,
    val deleted: Boolean,
)