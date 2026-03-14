package com.danucdev.thesimpsons.ui.screens.profile

import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danucdev.thesimpsons.MainActivity
import com.danucdev.thesimpsons.ui.core.TestTags.Companion.BURGER_MENU
import com.danucdev.thesimpsons.ui.core.TestTags.Companion.CHECK_BUTTON
import com.danucdev.thesimpsons.ui.core.TestTags.Companion.ERROR_MSG
import com.danucdev.thesimpsons.ui.core.TestTags.Companion.GENERIC_TEXT_FIELD
import com.danucdev.thesimpsons.ui.core.TestTags.Companion.NEXT_BUTTON
import com.danucdev.thesimpsons.ui.core.TestTags.Companion.PROFILE_NAVIGATION_ITEM
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileScreenKtTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun if_name_is_empty_show_error() {

        composeTestRule.onNodeWithTag(NEXT_BUTTON).performClick()
        composeTestRule.onNodeWithTag(NEXT_BUTTON).performClick()

        // Input name
        composeTestRule.onNodeWithTag(GENERIC_TEXT_FIELD).performTextInput("Damian")

        composeTestRule.onNodeWithTag(NEXT_BUTTON).performClick()

        // Check if don´t show error
        composeTestRule.onNodeWithTag(ERROR_MSG).assertIsNotDisplayed()

        // Open burger menu and click on profile nav item
        composeTestRule.onNodeWithTag(BURGER_MENU).performClick()
        composeTestRule.onNodeWithTag(PROFILE_NAVIGATION_ITEM).performClick()

        // Press change name button and check error
        composeTestRule.onNodeWithTag(CHECK_BUTTON).performClick()
        composeTestRule.onNodeWithTag(ERROR_MSG).isDisplayed()

    }

    @Test
    fun if_name_is_not_empty_clean_text_field_and_error_msg_is_not_displayed() {

        composeTestRule.onNodeWithTag(NEXT_BUTTON).performClick()
        composeTestRule.onNodeWithTag(NEXT_BUTTON).performClick()

        // Input name
        composeTestRule.onNodeWithTag(GENERIC_TEXT_FIELD).performTextInput("Damian")

        composeTestRule.onNodeWithTag(NEXT_BUTTON).performClick()

        // Check if don´t show error
        composeTestRule.onNodeWithTag(ERROR_MSG).assertIsNotDisplayed()

        // Open burger menu and click on profile nav item
        composeTestRule.onNodeWithTag(BURGER_MENU).performClick()
        composeTestRule.onNodeWithTag(PROFILE_NAVIGATION_ITEM).performClick()

        composeTestRule.onNodeWithTag(GENERIC_TEXT_FIELD).performTextInput("Test New Name")
        composeTestRule.onNodeWithTag(CHECK_BUTTON).performClick()

        // Check error msg does not display
        composeTestRule.onNodeWithTag(ERROR_MSG).isNotDisplayed()


    }


}