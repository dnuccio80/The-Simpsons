package com.danucdev.thesimpsons.ui.screens.characters

import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danucdev.thesimpsons.MainActivity
import com.danucdev.thesimpsons.ui.core.TestTags.Companion.ERROR_MSG
import com.danucdev.thesimpsons.ui.core.TestTags.Companion.GENERIC_TEXT_FIELD
import com.danucdev.thesimpsons.ui.core.TestTags.Companion.NEXT_BUTTON
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharactersScreenKtTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_first_access_do_not_allow_without_name(){

        composeRule.onNodeWithTag(NEXT_BUTTON).performClick()
        composeRule.onNodeWithTag(NEXT_BUTTON).performClick()
        composeRule.onNodeWithTag(NEXT_BUTTON).performClick()

        // Check if show error
        composeRule.onNodeWithTag(ERROR_MSG).assertExists()
    }

    @Test
    fun test_first_access_with_name_navigate_to_characters_screen(){

        composeRule.onNodeWithTag(NEXT_BUTTON).performClick()
        composeRule.onNodeWithTag(NEXT_BUTTON).performClick()

        // Input name
        composeRule.onNodeWithTag(GENERIC_TEXT_FIELD).performTextInput("Damian")

        composeRule.onNodeWithTag(NEXT_BUTTON).performClick()

        // Check if don´t show error
        composeRule.onNodeWithTag(ERROR_MSG).assertIsNotDisplayed()
    }

}