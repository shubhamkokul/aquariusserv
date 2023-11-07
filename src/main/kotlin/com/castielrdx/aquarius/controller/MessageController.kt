package com.castielrdx.aquarius.controller

import com.castielrdx.aquarius.data.Message
import com.castielrdx.aquarius.service.MessageService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class MessageController(val service: MessageService) {

    @GetMapping("/")
    suspend fun index(@RequestParam("name") name: String) = "Hello, $name!"

    @GetMapping("/messages")
    suspend fun getMessages(): List<Message> {
        println(service.findMessage().await())
        return service.findMessage().await()
    }

    @GetMapping("/{id}")
    suspend fun findMessageById(@PathVariable id: String): List<Message> {
        return service.findMessageById(id).await()
    }

    @PostMapping("/")
    suspend fun storeMessage(@RequestBody message: Message) {
       return service.storeMessage(message)
    }
}