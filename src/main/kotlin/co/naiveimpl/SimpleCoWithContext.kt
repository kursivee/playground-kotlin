package co.naiveimpl

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

    // I think  I need to build out the context first since I don't have code generation??

    val child = Context {
        println("DO STUFF")
    }
    val parent = Context()

    val child2 = Context {
        println("DO STUFF HERE")
    }
    val child3 = Context {
        println("DO STUFF HERE")
    }
    val parent2 = Context()

    parent.addChild(child)
    parent2.addChild(child2)
    parent2.addChild(child3)

    run(parent) {
        parent.children.forEach {
            launch(it, it.lambda)
        }
    }
    run(parent2) {
        parent2.children.forEach {
            launch(it, it.lambda)
        }
    }

}

// Convert this into a builder
fun run(context: Context, lambda: () -> Unit) {
    while(!context.complete) {
        Scheduler.schedule(context.children)
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

class Context(val lambda: () -> Unit = {}) {
    var complete = false
    var suspendTime: Long = -1
    var suspended = false
    var parent: Context? = null
    var children: MutableList<Context> = mutableListOf()

    fun addChild(child: Context) {
        child.parent = this
        children.add(child)
    }

    fun finish() {
        complete = true
        parent?.complete = true
    }
}