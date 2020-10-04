package com.buffup.sdk.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.buffup.sdk.R
import com.buffup.sdk.presentation.extensions.basicDiffUtil
import com.buffup.sdk.presentation.extensions.inflate
import com.buffup.sdk.presentation.modelView.AnswerModelView
import kotlinx.android.synthetic.main.buff_answer.view.*

class BuffAnswerAdapter(private val onClickAnswer: (AnswerModelView) -> Unit): RecyclerView.Adapter<BuffAnswerAdapter.ViewHolder>(){

    var buffAnswers: List<AnswerModelView> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new ->
            old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.buff_answer, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return buffAnswers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answer = buffAnswers[position]
        holder.bind(answer)

        holder.itemView.lnyAnswer.setOnClickListener {
            holder.itemView.lnyAnswer.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.selected_answer)
            onClickAnswer(answer)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(answerItem: AnswerModelView) {
            itemView.answerText.text = answerItem.title
            itemView.lnyAnswer.background = ContextCompat.getDrawable(itemView.context, R.drawable.light_bg)
        }
    }
}