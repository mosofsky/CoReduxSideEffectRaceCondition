# CoReduxSideEffectRaceCondition
This repository is meant to solve [Race condition for side effects](https://github.com/freeletics/CoRedux/issues/128) in [CoRedux](https://github.com/freeletics/CoRedux) Side Effects

As of this commit, the race condition has been resolved by implementing SideEffect rather than instantiating SimpleSideEffect. 

Only use SimpleSideEffect and CancellableSideEffect when you want to cancel the side effect if a new action of the same type comes in too quickly.

To see the original problem, go back to commit a7f7b155 ("Simplified test").  To see other solutions attempted, look at other branches such as feature/solve-by-using-multiple-actions.  

## Steps to demonstrate solution

You can demonstrate the solution manually or automatically using the automated test in the repository.

### Manual reproduction

1. Launch app in emulator
1. Leave "Add Delay" unchecked
1. Click "Do Async Task"
1. The output should read "[init, go, go, done, done]" 

The original problem could be worked around by introducing a delay between the two events which resulted in the following behavior:

1. Launch app in emulator
1. Check "Add Delay"
1. Click "Do Async Task"
1. The output should read "[init, go, done, go, done]"

### Automated reproduction

The two test cases above are automated as Espresso tests.  To observe their behavior, run as follows:

1. Open MainActivityTest in Android Studio
1. Click the green run button in the gutter to run the two tests
1. `shouldDoTwoAsyncTasksWithoutDelay` passes
1. `shouldDoTwoAsyncTasksWithDelay` passes 


