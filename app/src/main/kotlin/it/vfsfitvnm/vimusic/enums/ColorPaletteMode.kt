package it.vfsfitvnm.vimusic.enums

enum class ColorPaletteMode(private val descriptor: String) {
    Light("Светлый"),
    Dark("Темный"),
    System("Как в системе");

    override fun toString(): String {
        return descriptor
    }
}
