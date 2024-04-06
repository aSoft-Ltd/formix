package formix

import kollections.add
import kollections.buildMutableList
import kollections.isNotEmpty
import kollections.mutableListOf
import kollections.size

class DocumentBuilder(internal val name: String) {
    private val fields = mutableListOf<Field>()

    private val view = Splitter(
        common = View(
            uid = "common-view",
            label = "Common View",
            target = Target.common,
            pages = mutableListOf(),
            readonly = false,
            deleted = false,
        ),
        desktop = View(
            uid = "desktop-view",
            label = "Desktop View",
            target = Target.desktop,
            pages = mutableListOf(),
            readonly = false,
            deleted = false,
        ),
        mobile = View(
            uid = "mobile-view",
            label = "Mobile View",
            target = Target.mobile,
            pages = mutableListOf(),
            readonly = false,
            deleted = false,
        )
    )

    private val page = Splitter(
        common = Page(uid = "page-1", name = "Page 1", background = null, references = mutableListOf(), deleted = false),
        desktop = Page(uid = "page-1", name = "Page 1", background = null, references = mutableListOf(), deleted = false),
        mobile = Page(uid = "page-1", name = "Page 1", background = null, references = mutableListOf(), deleted = false),
    )

    private class Splitter<T>(
        var common: T,
        var desktop: T,
        var mobile: T
    )

    fun text(
        label: String,
        name: String = label.replace(" ", "-").lowercase(),
        uid: String = "field-${fields.size + 1}-$name",
        readonly: Boolean = false,
        deleted: Boolean = false,
        value: String? = null,
    ) {
        val field = TextField(uid, name, label, readonly, deleted, value)
        val ref = Reference(uid = field.uid, field = field.name, deleted = deleted, readonly = readonly)
        page.common.references.add(ref.copy(uid = "${field.uid}-${page.common.uid}"))
//        page.desktop.references.add(ref.copy(uid = "${field.uid}-${page.desktop.uid}"))
//        page.mobile.references.add(ref.copy(uid = "${field.uid}-${page.mobile.uid}"))
        fields.add(field)
    }

    fun page(name: String, uid: String = name.replace(" ", "-").lowercase()) {
        slidePages()

        page.common = Page(uid, name, null, references = mutableListOf(), deleted = false)
        page.desktop = Page(uid, name, null, references = mutableListOf(), deleted = false)
        page.mobile = Page(uid, name, null, references = mutableListOf(), deleted = false)
    }

    private fun slidePages() {
        if (page.common.references.isNotEmpty()) view.common.pages.add(page.common)
        if (page.desktop.references.isNotEmpty()) view.desktop.pages.add(page.desktop)
        if (page.mobile.references.isNotEmpty()) view.mobile.pages.add(page.mobile)
    }

    internal fun build(): Document {
        slidePages()
        val views = buildMutableList {
            if (view.common.pages.isNotEmpty()) add(view.common)
            if (view.desktop.pages.isNotEmpty()) add(view.desktop)
            if (view.mobile.pages.isNotEmpty()) add(view.mobile)
        }
        return Document(uid = "document", name = name, views = views, fields, readonly = false, deleted = false)
    }
}