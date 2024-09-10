package dev.vozniack.securo.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO) // #todo move config
class SecuroCoreApplication

fun main(args: Array<String>) {
	runApplication<SecuroCoreApplication>(*args)
}
