package com.buffup.sdk.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffup.sdk.data.api.BuffError
import com.buffup.sdk.domain.model.BuffResultModel
import com.buffup.sdk.domain.usecase.GetBuff
import com.buffup.sdk.presentation.modelView.BuffResultModelView
import com.buffup.sdk.presentation.modelView.toModelView
import com.buffup.sdk.presentation.utils.Event
import com.buffup.sdk.presentation.utils.Resource
import kotlinx.coroutines.launch
import kotlin.math.ceil

class BuffViewViewModel(private val getBuff: GetBuff): ViewModel(){

    private var countDownTimer: CountDownTimer? = null
    private var initTotalBuffer = 0
    private var totalBuffer = 5

    private val _buff = MutableLiveData< Resource<BuffResultModelView>>()
    val buff: LiveData<Resource<BuffResultModelView>> get() = _buff

    private val _setCountDownTimer: MutableLiveData<Event<Long>> by lazy { MutableLiveData<Event<Long>>() }
    val setCountDownTimer : LiveData<Event<Long>> get() = _setCountDownTimer

    private val _finishCountDownTimer: MutableLiveData<Event<Unit>> by lazy { MutableLiveData<Event<Unit>>() }
    val finishCountDownTimer : LiveData<Event<Unit>> get() = _finishCountDownTimer

    private val _hideBuff: MutableLiveData<Event<Unit>> by lazy { MutableLiveData<Event<Unit>>() }
    val hideBuff : LiveData<Event<Unit>> get() = _hideBuff

    fun init(){
        getBufferFromApi()
    }

    private fun getBufferFromApi() {
        var countDownTimerApiCall = object : CountDownTimer(MILLIS_API_CALL, MILLIS_TO_COUNT_DOWN) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                doBufferCall()
            }
        }.start()
    }

    private fun doBufferCall(){
        initTotalBuffer++

        viewModelScope.launch {
            getBuff(
                initTotalBuffer,
                onGetErrorBuff = {getError(it)},
                onGeBuffSuccess = {getBuffs(it)}
            )
        }
    }

    private fun getBuffs(buff: BuffResultModel){
        _buff.value = Resource.success(buff.toModelView())

        if(initTotalBuffer <= totalBuffer) {
            getBufferFromApi()
        }
    }

    private fun getError(buffError: BuffError) {
        _buff.value = Resource.error(buffError)
    }

    fun setCountDownTimer(timeToShow: Long) {
        countDownTimer = object : CountDownTimer(timeToShow, MILLIS_TO_COUNT_DOWN) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsUntilFinished : Long = ceil(millisUntilFinished.toDouble() / MILLIS_TO_COUNT_DOWN).toLong()
                _setCountDownTimer.value = Event((secondsUntilFinished))
            }

            override fun onFinish() {
                _finishCountDownTimer.value = Event((Unit))
            }
        }.start()
    }

    fun cancelCountDown(){
        countDownTimer?.cancel()
    }

    fun preparedToHideBuff(){
        val countDownTimer = object : CountDownTimer(
            MILLIS_TO_CLOSE,
            MILLIS_TO_COUNT_DOWN
        ) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                _hideBuff.value = Event((Unit))
            }
        }.start()
    }

    companion object {
        const val MILLIS_TO_COUNT_DOWN = 1000L
        const val MILLIS_TO_CLOSE = 2000L
        const val MILLIS_API_CALL = 30000L
    }
}