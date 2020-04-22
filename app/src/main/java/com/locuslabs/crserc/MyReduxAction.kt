package com.locuslabs.crserc

sealed class MyReduxAction {
    object InitAction : MyReduxAction()
    object TriggerAsyncAction1 : MyReduxAction()
    object TriggerAsyncAction2 : MyReduxAction()
    object AsyncFinishedAction : MyReduxAction()
}