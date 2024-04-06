import kotlin.test.Test
import formix.document
import kommander.expect
import kollections.size

class DocumentBuilderTest {
    @Test
    fun should_build_a_document() {
        val doc = document {
            text("First Name", value = "John")
            text("Last Name", value = "Doe")
        }

        expect(doc.fields.size).toBe(2)
    }
}