package com.locuslabs.crserc

import android.app.Application
import android.os.Handler
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.freeletics.coredux.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlin.coroutines.CoroutineContext

class MyViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    private val mutableState = MutableLiveData<MyReduxState>()

    private val performAsyncSideEffect = object : SideEffect<MyReduxState, MyReduxAction> {
        override val name: String = "my performAsyncSideEffect"

        override fun CoroutineScope.start(
            input: ReceiveChannel<MyReduxAction>,
            stateAccessor: StateAccessor<MyReduxState>,
            output: SendChannel<MyReduxAction>,
            logger: SideEffectLogger
        ): Job = viewModelScope.launch(context = CoroutineName(name)) {
            for (action in input) {
                when (action) {
                    MyReduxAction.TriggerAsyncAction -> {
                        output.send(MyReduxAction.AsyncFinishedAction)
                    }
                }
            }
        }
    }

    private val reduxStore = this.createStore(
        name = "MyRedux Store",
        initialState = MyReduxState(),
        sideEffects = listOf(
            performAsyncSideEffect
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
            MyReduxAction.InitAction -> {
                state.copy(
                    history = listOf("init")
                )
            }

            MyReduxAction.TriggerAsyncAction -> {
                state.copy(
                    history = state.history + listOf("go")
                )
            }

            MyReduxAction.AsyncFinishedAction -> {
                state.copy(
                    history = state.history + listOf("done")
                )
            }
        }
    }
}