package com.moodi.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GiphyAppModelTest {

    @Test
    fun `test properties for AppModel`() {
        val id = "123"
        val ageRate = "PG"
        val title = "Funny Gif"
        val animatedUrl = "https://example.com/animated.gif"
        val displayLink = "https://example.com/gif"
        val stillUrl = "https://example.com/still.gif"

        val giphyAppModel = GiphyAppModel(id, ageRate, title, animatedUrl, displayLink, stillUrl)

        // Verify the properties
        assertEquals(id, giphyAppModel.id)
        assertEquals(ageRate, giphyAppModel.ageRate)
        assertEquals(title, giphyAppModel.title)
        assertEquals(animatedUrl, giphyAppModel.animatedUrl)
        assertEquals(displayLink, giphyAppModel.displayLink)
        assertEquals(stillUrl, giphyAppModel.stillUrl)
    }

    @Test
    fun `test equal properties for AppModel`() {
        val giphyAppModel1 = GiphyAppModel(
            "123",
            "PG",
            "Funny Gif",
            "https://example.com/animated.gif",
            "https://example.com/gif",
            "https://example.com/still.gif"
        )

        val giphyAppModel2 = GiphyAppModel(
            "123",
            "PG",
            "Funny Gif",
            "https://example.com/animated.gif",
            "https://example.com/gif",
            "https://example.com/still.gif"
        )

        val differentGiphyAppModel = GiphyAppModel(
            "456",
            "PG",
            "Different Gif",
            "https://example.com/another.gif",
            "https://example.com/another-gif",
            "https://example.com/another-still.gif"
        )

        // Verify equals method
        assertEquals(giphyAppModel1, giphyAppModel2)
        assertNotEquals(giphyAppModel1, differentGiphyAppModel)
    }
}
