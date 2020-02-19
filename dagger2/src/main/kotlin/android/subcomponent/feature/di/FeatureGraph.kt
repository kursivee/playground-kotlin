package android.subcomponent.feature.di

import android.subcomponent.base.di.FeatureScope
import dagger.Subcomponent
import android.subcomponent.feature.HelloFeature

object FeatureGraph {
    @Subcomponent
    @FeatureScope
    interface FeatureComponent {
        fun inject(helloFeature: HelloFeature)

        @Subcomponent.Builder
        interface Builder {
            fun build(): FeatureComponent
        }
    }

    interface Graph {
        fun graphBuilder(): FeatureComponent.Builder
    }
}