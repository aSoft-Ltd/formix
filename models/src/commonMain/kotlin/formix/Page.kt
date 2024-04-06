package formix

interface Page {
    val uid: String
    val name: String
    val background: String?
    val references: List<Reference>
    val deleted: Boolean
}