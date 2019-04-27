package com.dev2019155.tictactoe

enum class Player {
    X, O;

    val nextPlayer: Player get() = when(this) {
        X -> O
        O -> X
    }
}