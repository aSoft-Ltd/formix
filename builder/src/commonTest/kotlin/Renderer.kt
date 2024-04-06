import formix.Document
import kotlinx.serialization.json.Json

private val codec = Json {
    prettyPrint = true
    prettyPrintIndent = "  "
}

internal fun Document.printToConsole() {
    println(codec.encodeToString(Document.serializer(), this))
}