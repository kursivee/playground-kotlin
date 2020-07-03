package com.kursivee.graphql

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class HelloWorldQuery: Query {
    fun helloWorld(): String = "Hello World"
}