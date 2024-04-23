package com.digi.music_player.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.digi.music_player.ui.screens.MusicPlayerViewModel
import com.digi.music_player.ui.screens.SongScreen


enum class Screens(val route: String) {
    PLAYER("player")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val packageName = context.packageName
    lateinit var player: ExoPlayer
    NavHost(navController = navController, startDestination = Screens.PLAYER.route) {
        composable(Screens.PLAYER.route) {
            // Player screen
            val vm: MusicPlayerViewModel = viewModel()
            player = ExoPlayer.Builder(context).build()
            val state = vm.playerState.collectAsState().value
            val playList = state.playList
            LaunchedEffect(Unit) {
                playList.forEach {
                    val path = "android.resource://" + packageName + "/" + it.music
                    val mediaItem = MediaItem.fromUri(Uri.parse(path))
                    player.addMediaItem(mediaItem)
                }
            }
            player.prepare()
            SongScreen(player, playList = playList)

        }
    }

}