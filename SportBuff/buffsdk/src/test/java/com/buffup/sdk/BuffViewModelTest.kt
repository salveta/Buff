package com.buffup.sdk

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.buffup.sdk.data.api.BuffError
import com.buffup.sdk.domain.model.BuffResultModel
import com.buffup.sdk.domain.usecase.GetBuff
import com.buffup.sdk.presentation.BuffViewViewModel
import com.buffup.sdk.presentation.modelView.AnswerModelView
import com.buffup.sdk.presentation.modelView.AuthorModelView
import com.buffup.sdk.presentation.modelView.BuffResultModelView
import com.buffup.sdk.presentation.modelView.QuestionModelView
import com.buffup.sdk.presentation.utils.Event
import com.buffup.sdk.presentation.utils.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BuffViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getBuff: GetBuff

    @Mock
    lateinit var buffResultModel: BuffResultModel

    private lateinit var vm: BuffViewViewModel
    private val buff: Observer<Resource<BuffResultModelView>> = mock()
    private val setCountDownTimer: Observer<Event<Long>> = mock()
    private val finishCountDownTimer: Observer<Event<Unit>> = mock()
    private val hideBuff: Observer<Event<Unit>> = mock()

    @Before
    fun setUp() {
        vm = BuffViewViewModel(getBuff)
    }

    @Test
    fun `Should hide view and reset values `() {
        vm.hideBuff.observeForever(hideBuff)
        vm.hideValue()

        verify(hideBuff).onChanged(any())
    }

    @Test
    fun `Should reset values, hide the buff and cancel de countdown`() {
        vm.finishCountDownTimer.observeForever(finishCountDownTimer)
        vm.finishCountDownTimer()

        verify(finishCountDownTimer).onChanged(any())
    }

    @Test
    fun `Should update the countDown`() {
        vm.setCountDownTimer.observeForever(setCountDownTimer)
        vm.updateCountdownTimer(2000L)

        verify(setCountDownTimer).onChanged(any())
    }

    @Test
    fun `Should get the buffs and update the view`() {
        vm.buff.observeForever(buff)
        vm.showBuff(createBuff())

        verify(buff).onChanged(any())
    }

    private fun createBuff(): BuffResultModelView{
        val question = QuestionModelView("How many goals score Messi?")
        val author = AuthorModelView("Salva", "image", "Perez")
        val answerOne = AnswerModelView(1, "3")
        val answerTwo = AnswerModelView(1, "3")
        val answerThree = AnswerModelView(1, "3")
        val answers = listOf( answerOne, answerTwo, answerThree)

        return BuffResultModelView(answers, author, question, 15)
    }
}