package android.subcomponent.util

object Injector {

    lateinit var injector: Any

    inline fun <reified T> getInject(): T {
        return injector as T
    }
}