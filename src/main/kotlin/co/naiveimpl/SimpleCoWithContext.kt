package co.naiveimpl

/**
 * Mental model of how I think delay would look?
 */
fun main() {

    /**
     // Scheduler
     wait 10ms then toggle isSuspended = false

     // Coroutine
     while(!context.isComplete) {
        context.schedule()
        launch(context.child) {
            // do stuff
        }
     }

     // launch(context) {
        if(!context.isSuspended) {
            // doStuff
        }
     }

     */

    val child = Context()
    val parent = Context()

    val child2 = Context()
    val parent2 = Context()

    parent.child = child
    parent2.child = child2

    run(parent) {
        launch(parent.child!!) {
            println("DO STUFF")
        }
    }
    run(parent2) {
        launch(parent2.child!!) {
            println("DO STUFF")
        }
    }

}

// Convert this into a builder
fun run(context: Context, lambda: () -> Unit) {
    while(!context.complete) {
        Scheduler.schedule(listOf(context.child!!))
        lambda()
    }
}

fun launch(context: Context, lambda: () -> Unit) {
    if(!context.complete && !context.suspended) {
        lambda()
        context.finish()
    }
}

object Scheduler {
    private const val timeSlice: Long = 5000

    fun schedule(contexts: List<Context>) {
        contexts.forEach { context ->
            if(context.suspendTime == -1L) {
                context.suspendTime = System.currentTimeMillis()
                context.suspended = true
            } else {
                context.suspended = System.currentTimeMillis() - context.suspendTime <= timeSlice
            }
        }
    }
}

class Context {
    var complete = false
    var suspendTime: Long = -1
    var suspended = false
    var parent: Context? = null
    var child: Context? = null
        set(value) {
            value!!.parent = this
            field = value
        }

    fun finish() {
        complete = true
        parent?.complete = true
    }
}