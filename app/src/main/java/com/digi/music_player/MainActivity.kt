package com.digi.music_player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.exoplayer.ExoPlayer
import com.digi.music_player.ui.AppNavigation
import com.digi.music_player.ui.theme.Music_playerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var player: ExoPlayer
        setContent {
            Music_playerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }


}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Music_playerTheme {

    }
}