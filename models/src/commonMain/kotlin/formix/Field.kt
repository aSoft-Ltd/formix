package formix

interface Field {
    val uid: String
    val name: String
    val label: String
    val readonly: Boolean
    val kind: String
    val deleted: Boolean
}