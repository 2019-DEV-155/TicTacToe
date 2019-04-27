package com.dev2019155.tictactoe

import com.winterbe.expekt.should
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `X always goes first`() {
        // region Arrange
        val game = Game()
        // endregion
        // region Act
        val currentPlayer = game.currentPlayer
        // endregion
        // region Assert
        currentPlayer.should.equal(Player.X)
        // endregion
    }

    @Test
    fun `Players cannot play on a played position`() {
        // region Arrange
        val game = Game()
        game.board[1][2] = Player.X
        // endregion
        // region Act
        assertThrows(IllegalArgumentException::class.java, { game[1, 2] = Player.O }, "Position 1, 2 is not EMPTY")
        // endregion
        // region Assert
        // Nothing to do here...
        // endregion
    }

    @Test
    fun `Player X cannot play for Player O`() {
        // region Arrange
        val game = Game()
        game.currentPlayer = Player.O
        // endregion
        // region Act
        assertThrows(java.lang.IllegalArgumentException::class.java, { game[1, 2] = Player.X }, "Player ${Player.X} cannot play for ${Player.O}")
        // endregion
        // region Assert
        // Nothing to do here...
        // endregion
    }

    @Test
    fun `Player O cannot play for Player X`() {
        // region Arrange
        val game = Game()
        game.currentPlayer = Player.X
        // endregion
        // region Act
        assertThrows(java.lang.IllegalArgumentException::class.java, { game[1, 2] = Player.O }, "Player ${Player.O} cannot play for ${Player.X}")
        // endregion
        // region Assert
        // Nothing to do here...
        // endregion
    }

    @Test
    fun `Players alternate placing X's and O's on the board`() {
        // region Arrange
        val game = Game()
        // endregion
        // region Act
        game[0, 0] = Player.X
        // endregion
        // region Assert
        game.currentPlayer.should.equal(Player.O)
        // endregion
    }

    @Test
    fun `Game is over as one player has three in a row horizontally`() = repeat(3) { row ->
        // region Arrange
        val game = Game()
        game.board.any { it.any { tile -> tile != null } }.should.be.`false`
        game.board[0][row] = Player.X
        game.board[1][row] = Player.X
        game.board[2][row] = Player.X
        // endregion
        // region Act
        val isOver = game.isOver
        // endregion
        // region Assert
        isOver.should.be.`true`
        // endregion
    }

    @Test
    fun `Game is over as one player has three in a row vertically`() = repeat(3) { column ->
        // region Arrange
        val game = Game()
        game.board.any { it.any { tile -> tile != null } }.should.be.`false`
        game.board[column][0] = Player.X
        game.board[column][1] = Player.X
        game.board[column][2] = Player.X
        // endregion
        // region Act
        val isOver = game.isOver
        // endregion
        // region Assert
        isOver.should.be.`true`
        // endregion
    }

    @Test
    fun `Game is over as one player has three in a row diagonally from the left`() {
        // region Arrange
        val game = Game()
        game.board.any { it.any { tile -> tile != null } }.should.be.`false`
        game.board[0][0] = Player.X
        game.board[1][1] = Player.X
        game.board[2][2] = Player.X
        // endregion
        // region Act
        val isOver = game.isOver
        // endregion
        // region Assert
        isOver.should.be.`true`
        // endregion
    }

    @Test
    fun `Game is over as one player has three in a row diagonally from the right`() {
        // region Arrange
        val game = Game()
        game.board.any { it.any { tile -> tile != null } }.should.be.`false`
        game.board[0][2] = Player.X
        game.board[1][1] = Player.X
        game.board[2][0] = Player.X
        // endregion
        // region Act
        val isOver = game.isOver
        // endregion
        // region Assert
        isOver.should.be.`true`
        // endregion
    }

    @Test
    fun `Game is over as all tiles are filled`() {
        // region Arrange
        val game = Game()
        /**
         *  X | O | X
         *  ---------
         *  O | X | O
         *  ---------
         *  O | X | O
         */
        game.board[0][0] = Player.X
        game.board[0][1] = Player.O
        game.board[0][2] = Player.X
        game.board[1][0] = Player.O
        game.board[1][1] = Player.X
        game.board[1][2] = Player.O
        game.board[2][0] = Player.O
        game.board[2][1] = Player.X
        game.board[2][2] = Player.O
        // endregion
        // region Act
        val isOver = game.isOver
        // endregion
        // region Assert
        isOver.should.be.`true`
        // endregion
    }

    @Test
    fun `Player wins horizontally`() = Player.values().forEach { player ->
        repeat(3) { row ->
            // region Arrange
            val game = Game()
            game.board.any { it.any { tile -> tile != null } }.should.be.`false`
            game.board[0][row] = player
            game.board[1][row] = player
            game.board[2][row] = player
            // endregion
            // region Act
            val winner = game.winner
            // endregion
            // region Assert
            winner.should.equal(player)
            // endregion
        }
    }

    @Test
    fun `Player wins vertically`() = Player.values().forEach { player ->
        repeat(3) { column ->
            // region Arrange
            val game = Game()
            game.board.any { it.any { tile -> tile != null } }.should.be.`false`
            game.board[column][0] = player
            game.board[column][1] = player
            game.board[column][2] = player
            // endregion
            // region Act
            val winner = game.winner
            // endregion
            // region Assert
            winner.should.equal(player)
            // endregion
        }
    }

    @Test
    fun `Player wins diagonally from left`() = Player.values().forEach { player ->
        // region Arrange
        val game = Game()
        game.board.any { it.any { tile -> tile != null } }.should.be.`false`
        game.board[0][0] = player
        game.board[1][1] = player
        game.board[2][2] = player
        // endregion
        // region Act
        val winner = game.winner
        // endregion
        // region Assert
        winner.should.equal(player)
        // endregion
    }

    @Test
    fun `Player wins diagonally from right`() = Player.values().forEach { player ->
        // region Arrange
        val game = Game()
        game.board.any { it.any { tile -> tile != null } }.should.be.`false`
        game.board[0][2] = player
        game.board[1][1] = player
        game.board[2][0] = player
        // endregion
        // region Act
        val winner = game.winner
        // endregion
        // region Assert
        winner.should.equal(player)
        // endregion
    }

    @Test
    fun `Game is a draw`() {
        // region Arrange
        val game = Game()
        /**
         *  X | O | X
         *  ---------
         *  O | X | O
         *  ---------
         *  O | X | O
         */
        game.board[0][0] = Player.X
        game.board[0][1] = Player.O
        game.board[0][2] = Player.X
        game.board[1][0] = Player.O
        game.board[1][1] = Player.X
        game.board[1][2] = Player.O
        game.board[2][0] = Player.O
        game.board[2][1] = Player.X
        game.board[2][2] = Player.O
        // endregion
        // region Act
        val winner = game.winner
        // endregion
        // region Assert
        winner.should.be.`null`
        // endregion
    }
}