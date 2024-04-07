import formix.document
import kollections.first
import kollections.get
import kollections.size
import kommander.expect
import kotlin.test.Test

class DocumentBuilderTest {
    @Test
    fun should_build_a_document() {
        val doc = document {
            text("First Name", value = "John")
            text("Last Name", value = "Doe")
        }

        expect(doc.fields.size).toBe(2)
        expect(doc.views.size).toBe(1)
        val desktop = doc.desktop().pages.first()
        expect(desktop.references.size).toBe(2)

        val mobile = doc.mobile().pages.first()
        expect(mobile.references.size).toBe(2)
    }

    @Test
    fun should_be_able_to_rename_the_first_page() {
        val doc = document {
            page("Basic Details")
            text("First Name", value = "John")
            text("Last Name", value = "Doe")
        }

        expect(doc.fields.size).toBe(2)
        expect(doc.views.size).toBe(1)
        val desktop = doc.desktop().pages.first()
        expect(desktop.name).toBe("Basic Details")

        val mobile = doc.mobile().pages.first()
        expect(mobile.name).toBe("Basic Details")
    }

    @Test
    fun should_be_able_to_page_these_documents() {
        val doc = document {
            text("First Name", value = "John")
            text("Last Name", value = "Doe")

            page("Page 2")
            text("Email")
        }

        expect(doc.fields.size).toBe(3)
        expect(doc.views.size).toBe(1)
        run { // desktop view
            val pages = doc.desktop().pages
            expect(pages.size).toBe(2)
            expect(pages[0].name).toBe("Page 1")
            expect(pages[1].name).toBe("Page 2")
        }

        run { // mobile view
            val pages = doc.mobile().pages
            expect(pages.size).toBe(2)
            expect(pages[0].name).toBe("Page 1")
            expect(pages[1].name).toBe("Page 2")
        }
    }
}