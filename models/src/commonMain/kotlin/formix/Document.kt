package formix

import kotlinx.serialization.Serializable
import kollections.MutableList
import kollections.find
import kollections.mutableListOf
import kollections.plus
import kollections.toMutableList

@Serializable
data class Document(
    val uid: String,
    var name: String,
    val views: MutableList<View>,
    val fields: MutableList<Field>,
    val readonly: Boolean = false,
    val deleted: Boolean = false,
) {
    fun desktop(): View {
        val common = views.find { it.target == Target.common }
        val desktop = views.find { it.target == Target.desktop }
        return when {
            common == null && desktop == null -> View("none", "Desktop View", Target.desktop, mutableListOf(), readonly, deleted)
            common == null && desktop != null -> desktop
            common != null && desktop == null -> common.copy(target = Target.desktop)
            common != null && desktop != null -> desktop.copy(pages = (common.pages + desktop.pages).toMutableList())
            else -> error("Unreachable code")
        }
    }

    fun mobile(): View {
        val common = views.find { it.target == Target.common }
        val mobile = views.find { it.target == Target.mobile }
        return when {
            common == null && mobile == null -> View("none", "Desktop View", Target.desktop, mutableListOf(), readonly, deleted)
            common == null && mobile != null -> mobile
            common != null && mobile == null -> common.copy(target = Target.desktop)
            common != null && mobile != null -> mobile.copy(pages = (common.pages + mobile.pages).toMutableList())
            else -> error("Unreachable code")
        }
    }
}
