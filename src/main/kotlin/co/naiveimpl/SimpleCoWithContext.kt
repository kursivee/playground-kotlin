package co.naiveimpl

/**
 * Mental model of how I think delay would look?
 */
fun main() {

    val C1 = "C1"
    val C2 = "C2"

    val context = Context(
        mapOf(C1 to 10000000.0, C2 to 100000.0),
        mutableMapOf(C1 to 0.0, C2 to 0.0),
        mutableMapOf(C1 to false, C2 to false)
    )

    while(context.hasNext()) {
        launch(context, C1) {
            if(it.counts[C1] != 0.0) {
                it.flags[C1] = true
                println("C1 done")
            }
        }
        launch(context, C2) {
            if(it.counts[C2] != 0.0) {
                it.flags[C2] = true
                println("C2 done")
            }
        }
    }
    println("Completed")
}

fun launch(context: Context, tag: String, lambda: (Context) -> Unit) {
    if(!context.isComplete(tag)) {
        if(context.counts[tag] == 0.0) {
            lambda(context)
            context.counts[tag] = context.counts[tag]!! + 1
        } else if(context.counts[tag]!! >= context.limits[tag]!!) {
            lambda(context)
        } else {
            context.counts[tag] = context.counts[tag]!! + .08
        }
    }
}

data class Context(
    val limits: Map<String, Double>,
    val counts: MutableMap<String, Double>,
    val flags: MutableMap<String, Boolean>
) {
    fun hasNext(): Boolean = flags.filter { !it.value }.isNotEmpty()
    fun isComplete(tag: String): Boolean = flags[tag] ?: true
}
