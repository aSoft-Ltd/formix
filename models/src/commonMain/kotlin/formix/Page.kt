package formix

interface Page {
    val uid: String
    val name: String
    val background: String?
    val fields: List<FieldReference>
    val deleted: Boolean
}