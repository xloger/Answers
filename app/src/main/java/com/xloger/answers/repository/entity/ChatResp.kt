package com.xloger.answers.repository.entity

import kotlinx.serialization.Serializable

@Serializable
data class ChatResp(
    val id: String,
    val `object`: String,
    val created: Int,
    val model: String,
    val usage: Usage,
    val choices: List<Choice>
) {
    @Serializable
    data class Usage(
        val prompt_tokens: Int,
        val completion_tokens: Int,
        val total_tokens: Int
    )

    @Serializable
    data class Choice(
        val message: Message,
//        val logprobs: Any?,
        val finish_reason: String,
        val index: Int
    ) {
        @Serializable
        data class Message(
            val role: String,
            val content: String
        )
    }
}