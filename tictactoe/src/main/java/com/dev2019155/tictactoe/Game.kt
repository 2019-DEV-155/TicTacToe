package com.dev2019155.tictactoe

import androidx.annotation.VisibleForTesting

class Game(
        internal val board: Array<Array<Player?>> = Array(3) { arrayOfNulls<Player?>(3) }
) {

    var currentPlayer: Player = Player.X
        internal set

    val isOver: Boolean
        get() = winner != null || board.all { it.all { tile -> tile != null } }

    val winner: Player?
        get() = getWinnerHorizontally() ?: getWinnerVertically() ?: getWinnerDiagonally()

    @Throws(IllegalArgumentException::class)
    operator fun set(column: Int, row: Int, player: Player) = if (board[column][row] == null) {
        board[column][row] = if (player == currentPlayer) {
            player
        } else throw IllegalArgumentException("Player $player cannot play for $currentPlayer")
        currentPlayer = player.nextPlayer
    } else throw IllegalArgumentException("Position $column, $row is not EMPTY")

    operator fun get(column: Int, row: Int) = board[column][row]

    private fun getWinnerHorizontally(): Player? = (0..2).asSequence().mapNotNull { column ->
        getPlayerIfIdenticalOn(column, 0, column, 1, column, 2)
    }.firstOrNull()

    private fun getWinnerVertically(): Player? = (0..2).asSequence().mapNotNull { row ->
        getPlayerIfIdenticalOn(0, row, 1, row, 2, row)
    }.firstOrNull()

    private fun getWinnerDiagonally(): Player? {
        return getPlayerIfIdenticalOn(0, 0, 1, 1, 2, 2) ?: getPlayerIfIdenticalOn(0, 2, 1, 1, 2, 0)
    }

    private fun getPlayerIfIdenticalOn(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int): Player? {
        val player = board[x1][y1]
        return if (board[x2][y2] == player && board[x3][y3] == player) {
            player
        } else null
    }
}

