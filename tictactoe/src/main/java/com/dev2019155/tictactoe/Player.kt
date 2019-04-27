package com.dev2019155.tictactoe

import androidx.annotation.DrawableRes

enum class Player(@DrawableRes val drawable: Int) {
    X(R.drawable.ic_x),
    O(R.drawable.ic_o);

    val nextPlayer: Player
        get() = when (this) {
            X -> O
            O -> X
        }
}