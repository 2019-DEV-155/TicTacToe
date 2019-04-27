package com.dev2019155.tictactoe

import androidx.annotation.VisibleForTesting

class Game {

    @VisibleForTesting
    internal val board = Array(3) { arrayOfNulls<Player?>(3) }

    var currentPlayer: Player = Player.O
        @VisibleForTesting internal set

    val isOver: Boolean = false

    val winner: Player? = null

    @Throws(IllegalArgumentException::class)
    operator fun set(column: Int, row: Int, player: Player) = Unit

}
