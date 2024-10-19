package com.gaming.stattracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StatTrackerApplication

fun main(args: Array<String>) {
	runApplication<StatTrackerApplication>(*args)
}
