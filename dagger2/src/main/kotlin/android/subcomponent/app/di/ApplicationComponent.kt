package android.subcomponent.app.di

import android.subcomponent.base.di.AppScope
import android.subcomponent.base.di.GreeterModule
import dagger.Component
import android.subcomponent.feature.di.FeatureGraph

@AppScope
@Component(
    modules = [GreeterModule::class]
)
interface ApplicationComponent: FeatureGraph.Graph