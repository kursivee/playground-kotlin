package featureflag

val featureStatus = mapOf(
    "F1" to true,
    "F2" to false
)

/**
 * Wrapping logic for feature flagging done in two different styles (DSL vs. Non DSL)
 */
fun main() {
    // DSL Style
    flagChoice("F1") {
        onEnabled {
            println("$it is enabled")
        }
    }

    flagChoice("F3") {
        onEnabled { println("$it is enabled") }
        onDisabled { println("$it is disabled") }
    }

    println("=====================")

    // Normal Style
    featureFlag("F1") {
        println("$it is enabled")
    }

    featureFlag("F1", {
        println("$it is disabled")
    }, {
        println("$it is enabled")
    })

    featureFlag("F2", {
        println("$it is disabled")
    }, {
        println("$it is enabled")
    })
}

/**
 * Feature flagging backed lambda params
 */
fun featureFlag(tag: String, onDisabled: ((String) -> Unit)? = null, onEnabled: ((String) -> Unit)? = null) {
    if(featureStatus.getOrDefault(tag, false)) {
        onEnabled?.invoke(tag)
    } else {
        onDisabled?.invoke(tag)
    }
}

/**
 * Feature flagging backed by a DSL builder-esque style
 */
fun flagChoice(tag: String, lambda: FeatureFlag.() -> Unit) {
    FeatureFlag(tag, featureStatus.getOrDefault(tag, false)).apply(lambda).execute()
}

class FeatureFlag(private val tag: String, private val enabled: Boolean) {

    var onEnabled: (String) -> Unit = {}
    var onDisabled: (String) -> Unit = {}

    infix fun onEnabled(lambda: (String) -> Unit): FeatureFlag {
        onEnabled = lambda
        return this
    }

    infix fun onDisabled(lambda: (String) -> Unit): FeatureFlag {
        onDisabled = lambda
        return this
    }

    fun execute() {
        if(enabled) {
            onEnabled(tag)
        } else {
            onDisabled(tag)
        }
    }
}
