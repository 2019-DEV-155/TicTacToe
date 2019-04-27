package com.dev2019155

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dev2019155.tictactoe.Game
import com.dev2019155.tictactoe.Player
import com.dev2019155.tictactoe.TicTacToeView

class MainActivity : AppCompatActivity(), TicTacToeView.Listener {

    private val tvStatus by lazy { findViewById<TextView>(R.id.tvStatus) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val ticTacToe = findViewById<TicTacToeView>(R.id.ttvTicTacToe)
        ticTacToe.listener = this
        tvStatus.text = getString(R.string.status_current_player_format, ticTacToe.game.currentPlayer)
        findViewById<Button>(R.id.btRestart).setOnClickListener { ticTacToe.restart() }
    }

    override fun onGameChanged(view: TicTacToeView, game: Game) {
        tvStatus.text = getString(R.string.status_current_player_format, game.currentPlayer)
    }

    override fun onGameOver(view: TicTacToeView, game: Game, winner: Player?) {
        tvStatus.text = if (winner != null) {
            getString(R.string.status_game_over_winner, winner)
        } else {
            getString(R.string.status_game_over_draw)
        }
    }
}