package it.vfsfitvnm.vimusic.ui.screens.settings

import android.text.format.Formatter
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import it.vfsfitvnm.vimusic.LocalPlayerAwareWindowInsets
import it.vfsfitvnm.vimusic.LocalPlayerServiceBinder
import it.vfsfitvnm.vimusic.enums.CoilDiskCacheMaxSize
import it.vfsfitvnm.vimusic.enums.ExoPlayerDiskCacheMaxSize
import it.vfsfitvnm.vimusic.ui.components.themed.Header
import it.vfsfitvnm.vimusic.ui.styling.LocalAppearance
import it.vfsfitvnm.vimusic.utils.coilDiskCacheMaxSizeKey
import it.vfsfitvnm.vimusic.utils.exoPlayerDiskCacheMaxSizeKey
import it.vfsfitvnm.vimusic.utils.rememberPreference

@OptIn(ExperimentalCoilApi::class)
@ExperimentalAnimationApi
@Composable
fun CacheSettings() {
    val context = LocalContext.current
    val (colorPalette) = LocalAppearance.current
    val binder = LocalPlayerServiceBinder.current

    var coilDiskCacheMaxSize by rememberPreference(
        coilDiskCacheMaxSizeKey,
        CoilDiskCacheMaxSize.`128MB`
    )
    var exoPlayerDiskCacheMaxSize by rememberPreference(
        exoPlayerDiskCacheMaxSizeKey,
        ExoPlayerDiskCacheMaxSize.`2GB`
    )

    Column(
        modifier = Modifier
            .background(colorPalette.background0)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                LocalPlayerAwareWindowInsets.current
                    .only(WindowInsetsSides.Vertical + WindowInsetsSides.End)
                    .asPaddingValues()
            )
    ) {
        Header(title = "Кеш")

        SettingsDescription(text = "Когда в кеше заканчивается место, ресурсы, к которым не было доступа в течение длительного времени, очищаются.")

        Coil.imageLoader(context).diskCache?.let { diskCache ->
            val diskCacheSize = remember(diskCache) {
                diskCache.size
            }

            SettingsGroupSpacer()

            SettingsEntryGroupText(title = "Кеш изображений")

            SettingsDescription(
                text = "${
                    Formatter.formatShortFileSize(
                        context,
                        diskCacheSize
                    )
                } используется (${diskCacheSize * 100 / coilDiskCacheMaxSize.bytes.coerceAtLeast(1)}%)"
            )

            EnumValueSelectorSettingsEntry(
                title = "Максимальный размер",
                selectedValue = coilDiskCacheMaxSize,
                onValueSelected = { coilDiskCacheMaxSize = it }
            )
        }

        binder?.cache?.let { cache ->
            val diskCacheSize by remember {
                derivedStateOf {
                    cache.cacheSpace
                }
            }

            SettingsGroupSpacer()

            SettingsEntryGroupText(title = "Кеш песен")

            SettingsDescription(
                text = buildString {
                    append(Formatter.formatShortFileSize(context, diskCacheSize))
                    append(" используется")
                    when (val size = exoPlayerDiskCacheMaxSize) {
                        ExoPlayerDiskCacheMaxSize.Unlimited -> {}
                        else -> append(" (${diskCacheSize * 100 / size.bytes}%)")
                    }
                }
            )

            EnumValueSelectorSettingsEntry(
                title = "Максимальный размер",
                selectedValue = exoPlayerDiskCacheMaxSize,
                onValueSelected = { exoPlayerDiskCacheMaxSize = it }
            )
        }
    }
}
