# Dagger2 Playground

## Goal
Find a way to leverage dagger in an android that allows for multi modular injection. Must be able to remove modules with minimal code changes to any layer.

Current layers are: Base, Feature, App

### Android.subcomponent

Playing around with dagger2 subcomponents. Verdict is probably not able to use since removing modules from app layer requires manual code changes. 

```
interface ApplicationComponent: FeatureGraph.Graph // Must manually remove graph
```

But theoretically, you should be able to generate the application component based on the Graphs loaded within the dependencies... but probably something worth investigating in the future. There's a much easier/proven way by defining a component in base.
