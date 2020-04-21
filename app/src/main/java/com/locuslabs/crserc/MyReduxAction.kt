package com.locuslabs.crserc

sealed class MyReduxAction {
    object InitAction : MyReduxAction()
    object TriggerAsyncAction : MyReduxAction()
    object AsyncFinishedAction : MyReduxAction()
}