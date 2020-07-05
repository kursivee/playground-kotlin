package co.session

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Creates a scope backed by a supervisor job to control coroutines
 * that should be called in a "session"
 *
 * All "global" coroutines that require an authenticated
 * user session should use [SessionScope]. On Logout, call [SessionScope.cancelChildren]
 * to terminate all coroutines under [SessionScope].
 */
object SessionScope: CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext + job

    fun cancelChildren() {
        job.cancelChildren()
    }
}

fun logout() {
    SessionScope.cancelChildren()
}

fun main(args: Array<String>) {
    runBlocking {
        sessionScopedTest("0")
        sessionScopedTest("1")
        delay(3000)
        println("LOGOUT")
        logout()
        delay(3000)
    }
}

suspend fun sessionScopedTest(tag: String) {
    SessionScope.launch {
        while(true) {
            delay(300)
            println("I'm in $tag")
        }
    }
}