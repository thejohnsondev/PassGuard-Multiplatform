package com.thejohnsondev.common.base

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.model.LoadingState
import com.thejohnsondev.model.ScreenState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BaseViewModelTest {

    private lateinit var viewModel: TestViewModel

    @BeforeTest
    fun setUp() {
        viewModel = TestViewModel()
    }

    @AfterTest
    fun tearDown() {
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun testLoading() = runTest {
        viewModel.publicLoading()
        val result = viewModel.publicGetScreenState().first()
        assertEquals(ScreenState.Loading, result)
    }

    @Test
    fun testShowContent() = runTest {
        viewModel.publicLoaded()
        val result = viewModel.publicGetScreenState().first()
        assertEquals(ScreenState.ShowContent, result)
    }

    @Test
    fun testLaunch() = runTest {
        val job = viewModel.publicLaunch {
            delay(100)
        }
        job.join()
        assertTrue(job.isCompleted)
    }

}