package android.subcomponent.feature

import android.subcomponent.base.Greeter
import android.subcomponent.feature.di.FeatureGraph
import android.subcomponent.util.Injector
import javax.inject.Inject

class HelloFeature {

    @Inject lateinit var greeter: Greeter

    init {
        Injector.getInject<FeatureGraph.Graph>().graphBuilder().build().inject(this)
    }

    fun greet(name: String) {
        println(greeter.greet(name))
    }
}