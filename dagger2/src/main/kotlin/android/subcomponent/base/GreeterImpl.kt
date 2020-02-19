package android.subcomponent.base

import javax.inject.Inject

class GreeterImpl @Inject constructor(): Greeter {
    override fun greet(name: String): String {
        return "Hello $name"
    }
}