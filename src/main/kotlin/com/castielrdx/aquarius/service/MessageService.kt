package com.castielrdx.aquarius.service

import com.castielrdx.aquarius.data.Message
import com.castielrdx.aquarius.req.cache.annotation.Memoize
import kotlinx.coroutines.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class MessageService(val db: JdbcTemplate) {

    @Memoize
    suspend fun findMessage(): Deferred<List<Message>> {
        val result = withContext(Dispatchers.IO) {
            async {
                db.query("select * from messages") { response, _ ->
                    Message(response.getString("id"), response.getString("text"))
                }
            }
        }
        return result
    }

    @Memoize
    suspend fun findMessageById(id: String): Deferred<List<Message>> {
        val result = withContext(Dispatchers.IO) {
            async {
                db.query("select * from messages where id = ?", id) { response, _ ->
                    Message(response.getString("id"), response.getString("text"))
                }
            }
        }
        return result
    }

    suspend fun storeMessage(message: Message) {
        val id = message.id ?: UUID.randomUUID().toString()
        db.update("insert into messages values (?, ?)", id, message.text)
    }
}