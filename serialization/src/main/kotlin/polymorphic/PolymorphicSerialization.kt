package polymorphic

import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

fun main() {
    val success = Result.stringify(Person.serializer(), Result.Success(Person("Mark")))
    val error = Result.stringify(Person.serializer(), Result.Error("error"))
    println(success)
    println(error)
    printJson(success)
    printJson(error)
}

fun printJson(json: String) {
    when(val data = Result.parse(Person.serializer(), json)) {
        is Result.Success -> println(data.data)
        is Result.Error -> println(data.error)
    }
}

@Serializable
sealed class Result<out T> {
    @Serializable
    data class Success<T>(val data: T): Result<T>()
    @Serializable
    data class Error<T>(val error: String): Result<T>()

    companion object {
        private fun <T> context(serializer: KSerializer<T>) = SerializersModule {
            polymorphic<Result<T>> {
                subclass(Success.serializer(serializer))
                subclass(Error.serializer(serializer))
            }
        }

        fun <T> stringify(serializer: KSerializer<T>, data: Result<T>): String {
            return Json(context = context(serializer))
                .stringify(PolymorphicSerializer(Result::class), data)
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> parse(serializer: KSerializer<T>, json: String): Result<T> {
            return Json(context = context(serializer))
                .parse(PolymorphicSerializer(Result::class), json) as Result<T>
        }
    }
}


@Serializable
data class Person(val name: String?)