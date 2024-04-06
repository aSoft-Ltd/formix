package formix

fun document(
    name: String = "Document",
    block: DocumentBuilder.() -> Unit,
): Document {
    val builder = DocumentBuilder(name)
    builder.block()
    return builder.build()
}