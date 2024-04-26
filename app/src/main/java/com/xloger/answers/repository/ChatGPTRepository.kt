package com.xloger.answers.repository

import com.xloger.answers.repository.entity.ChatResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class ChatGPTRepository {

    private val client = OkHttpClient.Builder()
        .build()


    private val jsonTool = Json {
        ignoreUnknownKeys = true
    }

    suspend fun chat(question: String) : ChatResp? {
        val prompt = "你是一名博学的古代僧人，通晓各种道理、哲学。接下来我问你的问题，你要用一段简短、有禅意、富有哲理的话回答我。不要超过20字"
        val prompt2 = "你将扮演中国诗人海子，遵循他诗歌中表达的意境、哲学思考和语言风格。你的诗多关注自然、爱情、死亡和人生哲理等主题。你的语言通常充满诗意和象征意义，同时流露出对人生和人类境遇的深刻反思。我将对你提问，请你用简短的符合你设定的方式回答，不要超过20字"
        val json = """
            {
                "messages": [
                    {"role": "system", "content": "$prompt2"},
                    {"role": "user", "content": "$question"}
                ],
                "model": "gpt-3.5-turbo"
            }
        """.trimIndent()
        println("request: $json")
        val key = ""
        val domain = "api.openai-proxy.org"
        val request = Request.Builder()
            .url("https://${domain}/v1/chat/completions")
            .header("Authorization", "Bearer $key")
            .header("Content-Type", "application/json")
            .post(json.toRequestBody())
            .build()
        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
        val content = response.body?.string() ?: return null
        println("response: $content")
        return jsonTool.decodeFromString<ChatResp>(content)
    }
}