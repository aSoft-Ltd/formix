package formix

class DocumentBuilder(internal val name: String) {

    fun text(
        label: String,
        name: String = label.replace(" ","-").lowercase(),
        value: String? = null
    ) {
        TODO()
    }

    internal fun build(): Document = TODO()
}