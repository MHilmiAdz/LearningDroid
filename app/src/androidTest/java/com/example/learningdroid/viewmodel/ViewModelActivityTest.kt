package com.example.learningdroid.viewmodel

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.learningdroid.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ViewModelActivityTest {

    private val dummyVolume = "Volume: 504.0"
    private val dummyCircumference = "Circumference: 100.0"
    private val dummySurfaceArea = "Surface Area: 396.0"
    private val dummyLength = "12.0"
    private val dummyWidth = "7.0"
    private val dummyHeight = "6.0"
    private val emptyInput = ""

    private val fieldEmptyLength = "Length is Empty"
    private val fieldEmptyWidth = "Width is Empty"
    private val fieldEmptyHeigt = "Height is Empty"

    @Before
    fun setup(){
        ActivityScenario.launch(ViewModelActivity::class.java)
    }

    @Test
    fun assertGetCircumference() {
        onView(withId(R.id.edt_length)).perform(typeText(dummyLength), closeSoftKeyboard())
        onView(withId(R.id.edt_width)).perform(typeText(dummyWidth), closeSoftKeyboard())
        onView(withId(R.id.edt_height)).perform(typeText(dummyHeight), closeSoftKeyboard())
        onView(withId(R.id.btn_calculate)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate)).perform(click())
        onView(withId(R.id.btn_calculate_circumference)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate_circumference)).perform(click())
        onView(withId(R.id.tv_result)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_result)).check(matches(withText(dummyCircumference)))
    }

    @Test
    fun assertGetVolume() {
        onView(withId(R.id.edt_length)).perform(typeText(dummyLength), closeSoftKeyboard())
        onView(withId(R.id.edt_width)).perform(typeText(dummyWidth), closeSoftKeyboard())
        onView(withId(R.id.edt_height)).perform(typeText(dummyHeight), closeSoftKeyboard())
        onView(withId(R.id.btn_calculate)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate)).perform(click())
        onView(withId(R.id.btn_calculate_volume)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate_volume)).perform(click())
        onView(withId(R.id.tv_result)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_result)).check(matches(withText(dummyVolume)))
    }

    @Test
    fun assertGetSurfaceArea() {
        onView(withId(R.id.edt_length)).perform(typeText(dummyLength), closeSoftKeyboard())
        onView(withId(R.id.edt_width)).perform(typeText(dummyWidth), closeSoftKeyboard())
        onView(withId(R.id.edt_height)).perform(typeText(dummyHeight), closeSoftKeyboard())
        onView(withId(R.id.btn_calculate)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate)).perform(click())
        onView(withId(R.id.btn_calculate_surface_area)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate_surface_area)).perform(click())
        onView(withId(R.id.tv_result)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_result)).check(matches(withText(dummySurfaceArea)))
    }

    @Test
    fun assertEmptyInput() {
        // pengecekan input untuk length
        onView(withId(R.id.edt_length)).perform(typeText(emptyInput), closeSoftKeyboard())
        onView(withId(R.id.btn_calculate)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate)).perform(click())
        onView(withId(R.id.edt_length)).check(matches(hasErrorText(fieldEmptyLength)))
        onView(withId(R.id.edt_length)).perform(typeText(dummyLength), closeSoftKeyboard())
        // pengecekan input untuk width
        onView(withId(R.id.edt_width)).perform(typeText(emptyInput), closeSoftKeyboard())
        onView(withId(R.id.btn_calculate)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate)).perform(click())
        onView(withId(R.id.edt_width)).check(matches(hasErrorText(fieldEmptyWidth)))
        onView(withId(R.id.edt_width)).perform(typeText(dummyWidth), closeSoftKeyboard())
        // pengecekan input untuk height
        onView(withId(R.id.edt_height)).perform(typeText(emptyInput), closeSoftKeyboard())
        onView(withId(R.id.btn_calculate)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate)).perform(click())
        onView(withId(R.id.edt_height)).check(matches(hasErrorText(fieldEmptyHeigt)))
        onView(withId(R.id.edt_height)).perform(typeText(dummyHeight), closeSoftKeyboard())
        onView(withId(R.id.btn_calculate)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_calculate)).perform(click())
    }

}