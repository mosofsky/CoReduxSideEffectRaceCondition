package com.locuslabs.crserc

import android.app.Application
import android.os.Handler
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.freeletics.coredux.SimpleSideEffect
import com.freeletics.coredux.createStore
import com.freeletics.coredux.subscribeToChangedStateUpdates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MyViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    private val mutableState = MutableLiveData<MyReduxState>()

    private val performIncrementCounterSideEffect = SimpleSideEffect<MyReduxState, MyReduxAction>("A SimpleSideEffect") {_, action, _, handler ->
        when (action) {
            MyReduxAction.IncrementCounterBy1Action -> handler {
                MyReduxAction.IncrementCounterBy2Action
            }
            else -> null
        }
    }

    private val reduxStore = this.createStore(
        name = "LocusLabs Redux Store",
        initialState = MyReduxState(),
        sideEffects = listOf(
            performIncrementCounterSideEffect
        ),
        reducer = ::reducer
    ).also {
        it.subscribeToChangedStateUpdates { newState: MyReduxState ->
            mutableState.value = newState
        }
    }

    val dispatchAction: (MyReduxAction) -> Unit = reduxStore::dispatch
    val dispatchMultipleActions: (List<MyReduxAction>) -> Unit = { actions ->
        actions.forEachIndexed { index, action ->
            Handler().postDelayed({
                dispatchAction(action)
            }, index * DISPATCH_ACTION_DELAY_MILLISECONDS)
        }
    }

    val state: LiveData<MyReduxState> = mutableState

    private fun reducer(state: MyReduxState, action: MyReduxAction): MyReduxState {
        Log.d(t, "reduce $action")

        return when (action) {
            MyReduxAction.InitCounterAction -> {
                val newCounter = 0
                state.copy(
                    counter = newCounter,
                    history = listOf("init($newCounter)")
                )
            }

            is MyReduxAction.IncrementCounterBy1Action -> {
                val newCounter = state.counter
                state.copy(
                    counter = newCounter,
                    history = state.history + listOf("by1($newCounter)")
                )
            }

            is MyReduxAction.IncrementCounterBy2Action -> {
                val newCounter = state.counter + 2
                state.copy(
                    counter = newCounter,
                    history = state.history + listOf("by2($newCounter)")
                )
            }
        }
    }
}