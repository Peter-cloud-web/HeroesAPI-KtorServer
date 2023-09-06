package com.example

import com.example.models.ApiResponse
import com.example.repository.HeroRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.*

class ApplicationTest {

    private val heroRepository: HeroRepository by inject(HeroRepository::class.java)

    @Test
    fun accessRootEndpoint_AssertCorrectInformation() {

        withTestApplication(moduleFunction = Application::module) {

            handleRequest(HttpMethod.Get, "/").apply {

                assertEquals(
                    expected = HttpStatusCode.OK, actual = response.status()
                )
                assertEquals(
                    expected = "Welcome to Heroes API", actual = response.content
                )
            }
        }
    }

    @Test
    fun accessAllHeroesEndpoint_AssertCorrectInformation() {

        withTestApplication(moduleFunction = Application::module) {

            handleRequest(HttpMethod.Get, "/anime/heroes").apply {

                assertEquals(
                    expected = HttpStatusCode.OK, actual = response.status()
                )
                val expected = ApiResponse(
                    success = true,
                    message = "ok",
                    prevPage = null,
                    nextPage = 2,
                    heroes = heroRepository.page1,
                )
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                println("EXPECTED: $expected")
                println("ACTUAL : $actual")
                assertEquals(
                    expected = expected, actual = actual
                )
            }
        }
    }


}
