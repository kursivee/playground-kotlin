package android.subcomponent.app

import android.subcomponent.app.di.DaggerApplicationComponent
import android.subcomponent.feature.HelloFeature
import android.subcomponent.util.Injector

fun main() {
    Injector.injector = DaggerApplicationComponent.create()
    HelloFeature().also {
        it.greet("Mark")
    }
}