package com.digi.music_player.ui.screens

import androidx.lifecycle.ViewModel
import com.digi.music_player.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


/***
 * Data class to represent a music in the list
 */
data class Music(
    val name: String = "",
    val artist: String = "",
    val music: Int = 0,
    val cover: Int = 0,
)

/***
 * Sealed class to represent the player events
 */
sealed class PlayerEvent {
    data object Play : PlayerEvent()
    data object Pause : PlayerEvent()
    data object Stop : PlayerEvent()
    data object Resume : PlayerEvent()
    data object Next : PlayerEvent()
    data object Previous : PlayerEvent()
    data class TogglePlayPause(val isPlaying: Boolean) : PlayerEvent()
    data class UpdateCurrentSong(val song: Music) : PlayerEvent()
}

/***
 * Data class to represent the player state
 */
data class PlayerState(
    var currentSong: Music = Music(),
    var playList: List<Music> = emptyList(),
    var currentPosition: Long = 0,
    var isPlaying: Boolean = false,
) {
    fun playNext() {
        val currentIndex = playList.indexOf(currentSong)
        val nextIndex = (currentIndex + 1) % playList.size
        currentSong = playList[nextIndex]
    }

    fun playPrevious() {
        val currentIndex = playList.indexOf(currentSong)
        val previousIndex = (currentIndex - 1) % playList.size
        currentSong = playList[previousIndex]
    }

    fun pause() {
        isPlaying = false
    }

    fun resume() {
        isPlaying = true
    }

    fun stop() {
        isPlaying = false
        currentPosition = 0
    }
}

/***
 * ViewModel to handle the player state and events
 */
class MusicPlayerViewModel : ViewModel() {

    private val _state = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _state.asStateFlow()

    init {
        _state.value = PlayerState(playList = getPlayList())
    }

    // Function to handle player events
    fun onEvent(event: PlayerEvent) {
        when (event) {
            is PlayerEvent.Play -> _state.value.resume()
            is PlayerEvent.Pause -> _state.value.pause()
            is PlayerEvent.Stop -> _state.value.stop()
            is PlayerEvent.Resume -> _state.value.resume()
            is PlayerEvent.UpdateCurrentSong -> _state.value.currentSong = event.song
            PlayerEvent.Next -> _state.value.playNext()
            PlayerEvent.Previous -> _state.value.playPrevious()
            is PlayerEvent.TogglePlayPause -> {
                if (event.isPlaying) {
                    _state.value.resume()
                } else {
                    _state.value.pause()
                }
            }
        }
    }

    /***
     * Return a play list of type Music data class
     */
    private fun getPlayList(): List<Music> {
        return listOf(
            Music(
                name = "Master Of Puppets",
                artist = "Metallica",
                cover = R.drawable.master_of_puppets_album_cover,
                music = R.raw.master_of_puppets
            ),
            Music(
                name = "Everyday Normal Guy 2",
                artist = "Jon Lajoie",
                cover = R.drawable.everyday_normal_guy_2_album_cover,
                music = R.raw.everyday_normal_guy_2
            ),
            Music(
                name = "Lose Yourself",
                artist = "Eminem",
                cover = R.drawable.lose_yourself_album_cover,
                music = R.raw.lose_yourself
            ),
            Music(
                name = "Crazy",
                artist = "Gnarls Barkley",
                cover = R.drawable.crazy_album_cover,
                music = R.raw.crazy
            ),
            Music(
                name = "Till I Collapse",
                artist = "Eminem",
                cover = R.drawable.till_i_collapse_album_cover,
                music = R.raw.till_i_collapse
            ),
        )
    }
}