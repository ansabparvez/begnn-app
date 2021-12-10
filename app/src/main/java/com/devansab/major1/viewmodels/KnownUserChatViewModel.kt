package com.devansab.major1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.devansab.major1.data.entities.LastMessage
import com.devansab.major1.data.entities.Message
import com.devansab.major1.data.repositories.LastMessageRepository
import com.devansab.major1.data.repositories.MessageRepository
import com.devansab.major1.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class KnownUserChatViewModel(application: Application) : AndroidViewModel(application) {
    private val messageRepository = MessageRepository(application)
    private val userRepository = UserRepository(application)
    private val lastMessageRepository = LastMessageRepository(application)

    fun getUserByUsername(username: String) = userRepository.getUserByUsername(username)

    fun getAllMessagesOfUser(username: String, isAnonymous: Boolean): Flow<List<Message>> {
        val anonymous = if (isAnonymous) 1 else 0
        return messageRepository.getAllMessagesOfUser(username, anonymous)
    }

    suspend fun sendMessage(message: Message, name: String) {
        val lastMessage = LastMessage(message.userName, message.text, message.time, name, false)
        lastMessageRepository.updateLastMessage(lastMessage)
        messageRepository.sendMessageToKnownUser(message)
    }
}