package com.buffup.sdk.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.buffup.sdk.R
import com.buffup.sdk.presentation.adapter.BuffAnswerAdapter
import com.buffup.sdk.presentation.extensions.invisible
import com.buffup.sdk.presentation.extensions.loadRoundedImage
import com.buffup.sdk.presentation.extensions.visible
import com.buffup.sdk.presentation.modelView.BuffResultModelView
import com.buffup.sdk.presentation.utils.EventObserver
import com.buffup.sdk.presentation.utils.Resource
import kotlinx.android.synthetic.main.buff_answer_list.view.*
import kotlinx.android.synthetic.main.buff_container.view.*
import kotlinx.android.synthetic.main.buff_question.view.*
import kotlinx.android.synthetic.main.buff_sender.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class BuffView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs), KoinComponent {

    private val vModel: BuffViewViewModel by inject()
    private var bufferAnswersAdapter: BuffAnswerAdapter? = null
    private var countDownStatus = MINIMUM_COUNTER

    init {
        View.inflate(context, R.layout.buff_container, this)
        vModel.init()
        loadObservers()
        initAdapter()
    }

    private fun loadObservers(){
        vModel.buff.observe(context as LifecycleOwner, Observer { buff ->
            when(buff.status){
                Resource.Status.SUCCESS -> showBuff(buff.data as BuffResultModelView)
                Resource.Status.ERROR -> showError()
            }
        })

        vModel.setCountDownTimer.observe(context as LifecycleOwner, EventObserver{ seconds ->
            updateValues(seconds)
        })

        vModel.finishCountDownTimer.observe(context as LifecycleOwner, EventObserver{
            resetValues()
            cancelCountDown()
            hideBuff()
        })

        vModel.hideBuff.observe(context as LifecycleOwner, EventObserver{
            resetValues()
            hideBuff()
        })

        buffClose.setOnClickListener {
            hideBuff()
        }
    }

    private fun initAdapter(){
        bufferAnswersAdapter = BuffAnswerAdapter{
            stopCountDown()
            vModel.preparedToHideBuff()
        }

        rvBuffAnswer.layoutManager = LinearLayoutManager(context)
        rvBuffAnswer.adapter = bufferAnswersAdapter
    }

    private fun showBuff(buff: BuffResultModelView){
        cnsContainerBuff.visible()
        doAnimationBuff(R.anim.entry_anim)

        senderName.text = context.getString(
            R.string.author_name,
            buff.author.firstName, buff.author.lastName
        ).trim()
        senderImage.loadRoundedImage(buff.author.image)

        questionText.text = buff.question.title
        questionTimeProgress.max = buff.timeToShow
        vModel.setCountDownTimer(buff.timeToShow.toLong() *MILLIS_TO_COUNT_DOWN)
        bufferAnswersAdapter?.buffAnswers = buff.answers
    }

    private fun showError(){
        // TODO check if needed to show an error
    }

    private fun updateValues(secondsUntilFinished : Long ){
        questionTime.text = secondsUntilFinished.toString()
        questionTimeProgress.progress = secondsUntilFinished.toInt()
        countDownStatus = secondsUntilFinished.toString()
    }

    private fun resetValues(){
        questionTime.text = MINIMUM_COUNTER
        questionTimeProgress.progress = MINIMUM_PROGRESS
    }

    private fun hideBuff(){
        cnsContainerBuff.invisible()
        doAnimationBuff(R.anim.exit_anim)
        cancelCountDown()
    }

    private fun cancelCountDown(){
        vModel.cancelCountDown()
    }

    private fun stopCountDown(){
        cancelCountDown()
    }

    private fun doAnimationBuff(animationType: Int){
        val animation = AnimationUtils.loadAnimation(context, animationType)
        cnsContainerBuff.startAnimation(animation)
    }

    companion object {
        const val MILLIS_TO_COUNT_DOWN = 1000L
        const val MINIMUM_PROGRESS = 0
        const val MINIMUM_COUNTER = "0"
    }
}