package com.xloger.answers.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class ChatGPTRepository {

    private val client = OkHttpClient.Builder()
        .build()


    suspend fun chat(question: String) : String {
        val json = """
            {
                "messages": [{"role": "user", "content": "${question}"}],
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
        val content = response.body?.string()
        println("response: $content")
        return content ?: ""
    }
}