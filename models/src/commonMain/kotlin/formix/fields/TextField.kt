@file:Suppress("PackageDirectoryMismatch")

package formix

import formix.Field
import kotlinx.serialization.Serializable

@Serializable
data class TextField(
    override var uid: String,
    override var name: String,
    override var label: String,
    override var readonly: Boolean,
    override var deleted: Boolean,
    var value: String
) : Field