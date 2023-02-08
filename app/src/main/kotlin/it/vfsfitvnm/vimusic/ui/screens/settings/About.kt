package it.vfsfitvnm.vimusic.ui.screens.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import it.vfsfitvnm.vimusic.BuildConfig
import it.vfsfitvnm.vimusic.LocalPlayerAwareWindowInsets
import it.vfsfitvnm.vimusic.ui.components.themed.Header
import it.vfsfitvnm.vimusic.ui.styling.LocalAppearance
import it.vfsfitvnm.vimusic.utils.secondary

@ExperimentalAnimationApi
@Composable
fun About() {
    val (colorPalette, typography) = LocalAppearance.current
    val uriHandler = LocalUriHandler.current

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
        Header(title = "О нас") {
            BasicText(
                text = "v${BuildConfig.VERSION_NAME} от vfsfitvnm",
                style = typography.s.secondary
            )
        }

        SettingsEntryGroupText(title = "Связь")

        SettingsEntry(
            title = "GitHub",
            text = "Просмотреть исходный код",
            onClick = {
                uriHandler.openUri("https://github.com/vfsfitvnm/ViMusic")
            }
        )

        SettingsEntry(
            title = "GitHub",
            text = "Просмотреть исходный код (русифицированое приложение)",
            onClick = {
                uriHandler.openUri("https://github.com/AnikinDmitry/ViMusic-for-Russia")
            }
        )

        SettingsGroupSpacer()

        SettingsEntryGroupText(title = "Устранение неисправностей")

        SettingsEntry(
            title = "Сообщить о проблеме",
            text = "Вы будете перенаправлены на GitHub",
            onClick = {
                uriHandler.openUri("https://github.com/vfsfitvnm/ViMusic/issues/new?assignees=&labels=bug&template=bug_report.yaml")
            }
        )

        SettingsEntry(
            title = "Запросить функцию или предложить идею",
            text = "Вы будете перенаправлены на GitHub",
            onClick = {
                uriHandler.openUri("https://github.com/vfsfitvnm/ViMusic/issues/new?assignees=&labels=enhancement&template=feature_request.yaml")
            }
        )
    }
}
