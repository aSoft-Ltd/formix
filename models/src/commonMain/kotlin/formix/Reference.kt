package formix

interface Reference {
    val uid: String
    val field: String
    val readonly: Boolean
    val deleted: Boolean
}