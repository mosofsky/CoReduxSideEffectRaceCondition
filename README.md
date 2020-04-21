# CoReduxSideEffectRaceCondition
This repository is meant to reproduce [Race condition for side effects](https://github.com/freeletics/CoRedux/issues/128) in [CoRedux](https://github.com/freeletics/CoRedux) Side Effects

## Steps to reproduce

You can reproduce the problem manually or automatically using the automated test in the repository.

### Manual reproduction

1. Launch app in emulator
1. Leave "Add Delay" unchecked
1. Click "Increment by Two"
1. The output should read "[init(0), by1(0), by2(2), by1(2), by2(4)]" but instead it reads "[init(0), by1(0), by1(0), by2(2)]"

The desired behavior is possible if you add the delay, as follows:

1. Launch app in emulator
1. Check "Add Delay"
1. Click "Increment by Two"
1. The output should read "[init(0), by1(0), by2(2), by1(2), by2(4)]"

### Automated reproduction

The two test cases above are automated as Espresso tests.  To observe their behavior, run as follows:

1. Open MainActivityTest in Android Studio
1. Click the green run button in the gutter to run the two tests
1. `shouldIncrementTwiceWithDelay` passes as expected
1. `shouldIncrementTwiceWithoutDelay` should pass like `shouldIncrementTwiceWithDelay` but it fails


