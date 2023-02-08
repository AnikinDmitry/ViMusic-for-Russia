package it.vfsfitvnm.vimusic.enums

enum class ColorPaletteName(private val descriptor: String) {
    Default("По умолчанию"),
    Dynamic("Динамическая"),
    PureBlack("Чистый черный");

    override fun toString(): String {
        return descriptor
    }
}
