package com.dev2019155.tictactoe

import android.content.Context
import android.graphics.drawable.Animatable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import androidx.core.content.ContextCompat


class TicTacToeView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GridView(context, attrs, defStyleAttr) {

    var listener: Listener? = null
    var game: Game = Game()
        private set

    init {
        numColumns = 3
        columnWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48f, resources.displayMetrics).toInt()
        stretchMode = NO_STRETCH
        adapter = Adapter()
    }

    fun restart() {
        game = Game()
        (adapter as Adapter).notifyDataSetChanged()
        listener?.onGameChanged(this, game)
    }

    inner class Adapter : BaseAdapter() {

        override fun getCount() = 9

        override fun getItemId(position: Int) = position.toLong()

        override fun getItem(position: Int): Player? = game[position / 3, position % 3]

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getView(convertView, parent).apply {
                val player = getItem(position)
                setImageDrawable(player?.let { ContextCompat.getDrawable(context, it.drawable) })
                setContentDescription(player)
            }
        }

        private fun getView(convertView: View?, parent: ViewGroup): ImageView {
            if (convertView != null) {
                return convertView as ImageView
            }
            return (LayoutInflater.from(parent.context).inflate(R.layout.tictactoe_tile, parent, false) as ImageView).apply {
                setOnClickListener {
                    val position = getPositionForView(it)
                    if (getItem(position) == null && !game.isOver) {
                        val drawable = ContextCompat.getDrawable(context, game.currentPlayer.drawable)
                        setContentDescription(game.currentPlayer)
                        game[position / 3, position % 3] = game.currentPlayer
                        setImageDrawable(drawable)
                        (drawable as? Animatable)?.start()
                        if (game.isOver) {
                            listener?.onGameOver(this@TicTacToeView, game, game.winner)
                        } else {
                            listener?.onGameChanged(this@TicTacToeView, game)
                        }
                    }
                }
            }
        }

        private fun ImageView.setContentDescription(player: Player?) {
            contentDescription = player?.let {
                context.getString(R.string.game_tile_player_format, player)
            } ?: context.getString(R.string.empty_game_tile)
        }
    }

    override fun onSaveInstanceState(): Parcelable? = SavedState(super.onSaveInstanceState(), game)

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedSate = state as SavedState
        super.onRestoreInstanceState(savedSate.superState)
        game = Game(savedSate.board)
        game.currentPlayer = savedSate.currentPlayer
        listener?.onGameChanged(this, game)
    }

    class SavedState : BaseSavedState {

        val board: Array<Array<Player?>>
        val currentPlayer: Player

        constructor(superState: Parcelable?, game: Game) : super(superState) {
            board = game.board
            currentPlayer = game.currentPlayer
        }

        private constructor(parcel: Parcel) : super(parcel) {
            board = Array(3) { arrayOfNulls<Player?>(3) }
            repeat(3) { row ->
                repeat(3) { column ->
                    board[row][column] = parcel.readInt().let {
                        if (it != 1) {
                            Player.values()[it]
                        } else null
                    }
                }
            }
            currentPlayer = Player.values()[parcel.readInt()]
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            repeat(3) { row ->
                repeat(3) { column ->
                    out.writeInt(board[row][column]?.ordinal ?: -1)
                }
            }
            out.writeInt(currentPlayer.ordinal)
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel) = SavedState(parcel)
            override fun newArray(size: Int) = arrayOfNulls<SavedState>(size)
        }
    }

    interface Listener {
        fun onGameChanged(view: TicTacToeView, game: Game)
        fun onGameOver(view: TicTacToeView, game: Game, winner: Player?)
    }
}