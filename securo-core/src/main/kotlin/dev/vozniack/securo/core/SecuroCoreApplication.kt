package dev.vozniack.securo.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecuroCoreApplication

fun main(args: Array<String>) {
	runApplication<SecuroCoreApplication>(*args)
}
