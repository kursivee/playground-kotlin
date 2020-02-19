package android.subcomponent.base.di

import android.subcomponent.base.Greeter
import android.subcomponent.base.GreeterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class GreeterModule {
    @AppScope
    @Binds
    abstract fun provideGreeter(greeterImpl: GreeterImpl): Greeter
}