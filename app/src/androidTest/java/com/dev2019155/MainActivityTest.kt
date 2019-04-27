package com.dev2019155

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.dev2019155.tictactoe.Player
import org.hamcrest.Description
import org.hamcrest.Matchers.anything
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testLaunch() {
        onView(withId(R.id.ttvTicTacToe)).check(matches(isDisplayed()))
        onView(withId(R.id.tvStatus)).check(matches(isDisplayed()))
        currentPlayer(Player.X)
        onView(withId(R.id.btRestart)).check(matches(isDisplayed()))
    }

    @Test
    fun testRestart() {
        currentPlayer(Player.X)
        click(0)
        matchDrawable(0, Player.X.drawable)
        currentPlayer(Player.O)
        click(2)
        matchDrawable(2, Player.O.drawable)
        currentPlayer(Player.X)
        click(1)
        matchDrawable(1, Player.X.drawable)
        currentPlayer(Player.O)
        onView(withId(R.id.btRestart)).perform(click())
        currentPlayer(Player.X)
        matchDrawable(0, -1)
        matchDrawable(1, -1)
        matchDrawable(2, -1)
    }

    @Test
    fun testDraw() {
        currentPlayer(Player.X)
        click(0)
        matchDrawable(0, Player.X.drawable)
        currentPlayer(Player.O)
        click(2)
        matchDrawable(2, Player.O.drawable)
        currentPlayer(Player.X)
        click(1)
        matchDrawable(1, Player.X.drawable)
        currentPlayer(Player.O)
        click(3)
        matchDrawable(3, Player.O.drawable)
        currentPlayer(Player.X)
        click(4)
        matchDrawable(4, Player.X.drawable)
        currentPlayer(Player.O)
        click(7)
        matchDrawable(7, Player.O.drawable)
        currentPlayer(Player.X)
        click(5)
        matchDrawable(5, Player.X.drawable)
        currentPlayer(Player.O)
        click(8)
        matchDrawable(8, Player.O.drawable)
        currentPlayer(Player.X)
        click(6)
        matchDrawable(6, Player.X.drawable)
        onView(withId(R.id.tvStatus)).check(matches(withText(R.string.status_game_over_draw)))
    }

    @Test
    fun testWin() {
        currentPlayer(Player.X)
        click(0)
        matchDrawable(0, Player.X.drawable)
        currentPlayer(Player.O)
        click(2)
        matchDrawable(2, Player.O.drawable)
        currentPlayer(Player.X)
        click(3)
        matchDrawable(3, Player.X.drawable)
        currentPlayer(Player.O)
        click(5)
        matchDrawable(5, Player.O.drawable)
        currentPlayer(Player.X)
        click(6)
        matchDrawable(6, Player.X.drawable)

        onView(withId(R.id.tvStatus)).check(matches(withText(rule.activity.getString(R.string.status_game_over_winner, Player.X))))
    }

    private fun currentPlayer(player: Player) {
        onView(withId(R.id.tvStatus)).check(matches(withText(rule.activity.getString(R.string.status_current_player_format, player))))
    }

    private fun click(position: Int) {
        onData(anything()).inAdapterView(withId(R.id.ttvTicTacToe)).atPosition(position).perform(click())
    }

    private fun matchDrawable(position: Int, drawable: Int) {
        onData(anything()).inAdapterView(withId(R.id.ttvTicTacToe)).atPosition(position).check(matches(DrawableMatcher(drawable)))
    }

    class DrawableMatcher(@DrawableRes private val expectedId: Int) : TypeSafeMatcher<View>() {

        override fun matchesSafely(target: View): Boolean {
            if (target !is ImageView) {
                return false
            }
            if (expectedId < 0) {
                return target.drawable == null
            }
            val expectedDrawable = ContextCompat.getDrawable(target.context, expectedId)
                    ?: return false
            val bitmap = getBitmap(target.drawable)
            val otherBitmap = getBitmap(expectedDrawable)
            return bitmap.sameAs(otherBitmap)
        }

        private fun getBitmap(drawable: Drawable): Bitmap {
            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                    drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        override fun describeTo(description: Description) = Unit
    }
}