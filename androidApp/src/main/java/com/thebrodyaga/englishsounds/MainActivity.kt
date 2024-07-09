package com.thebrodyaga.englishsounds

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.thebrodyaga.englishsounds.feature.audioPlayer.api.AudioPlayer
import com.thebrodyaga.englishsounds.feature.audioPlayer.api.Player
import com.thebrodyaga.englishsounds.root.api.PlatformDependencies
import com.thebrodyaga.englishsounds.root.api.RootComponent
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

private class AndroidPlatformDependencies(
    override val audioPlayer: AudioPlayer,
) : PlatformDependencies

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val systemBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        enableEdgeToEdge(statusBarStyle = systemBarStyle, navigationBarStyle = systemBarStyle)
        super.onCreate(savedInstanceState)
        val koin = koinApplication {
            modules(
                module {
                    single<ComponentActivity> { this@MainActivity }
                    singleOf(::Player) bind AudioPlayer::class
                    singleOf(::AndroidPlatformDependencies) bind PlatformDependencies::class
                }
            )
        }
        val root = RootComponent(
            platformDependencies = koin.koin.get(),
            componentContext = defaultComponentContext()
        )
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Box(modifier = Modifier.safeDrawingPadding()) {
        Text(
            text = text
        )
    }

}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
