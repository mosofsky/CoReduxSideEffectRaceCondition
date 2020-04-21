package com.locuslabs.crserc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private val myViewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel.state.observe(this, Observer { render() })

        val actions = listOf(
                MyReduxAction.InitCounterAction,
                MyReduxAction.IncrementCounterBy1Action,
                MyReduxAction.IncrementCounterBy1Action
        )

        findViewById<Button>(R.id.incrementByTwoButton).setOnClickListener {
            if (findViewById<Switch>(R.id.addDelay).isChecked) {
                myViewModel.dispatchMultipleActions(actions)
            } else {
                actions.forEach { myViewModel.dispatchAction(it) }
            }
        }
    }

    fun state(): MyReduxState {
        return myViewModel.state.value!!
    }

    private fun render() {
        findViewById<TextView>(R.id.counterTextView).text = state().history.toString()
    }
}