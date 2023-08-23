package com.example.springkotlinvaluesinconstructors

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SpringKotlinValuesInConstructorsApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinValuesInConstructorsApplication>(*args)
}
@Service
class FooService(
    @Value("\${foo.bar}")
    val someStrWithValueAnno: String  // should be no error
)
@RestController
class FooController(
    private val fooService: FooService,
    private val someStrWithInitValue: String = "!!!",  // should be no error
    private val barService: BarService? = null
) {
    @GetMapping
    fun foo():String {
        println(barService)
        return fooService.someStrWithValueAnno+someStrWithInitValue
    }
}
interface BarService

@Configuration
class ConfigurationTest {

    @Bean
    @ConditionalOnProperty(name = ["foo.buzz"], havingValue = "true")
    fun barService(): BarService {
        return object : BarService {}
    }
}


@Component
class CompTest(
   // foo: FooService, // in case of multiple parameters the gutter icon is shown
   // barService: BarService?  // in case of not required NOT initalized single parameter the gutter IS shown
    barService: BarService? = null // in case of not required and initalized single parameter no gutter is shown
) {

}