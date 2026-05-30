package com.example.kotlinmultiplatform.core.network

import io.ktor.client.HttpClient

 expect fun createHttpClient(): HttpClient
