package com.moodi.data.mock

import com.google.gson.Gson
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

/**
 * This class is used to read the json file from the test resources folder and convert it to string
 */
class MockUtil {

    companion object {
        const val PATH = "api-response/"
        const val SEARCH_MODEL = "search-model.json"
        const val RANDOM_MODEL = "random-model.json"

        /**
         * In order to test more then one item in the list, we can use the same model as a duplicate because the data it self is not important here.
         */
        const val SEARCH_MODEL_LIST = "search-model-duplicate.json"
        const val SEARCH_MODEL_EMPTY = "search-model-empty.json"
    }

    /**
     * This function is used to read the json file from the test resources folder and convert it to string
     * @param fileName the name of the json file under [PATH]
     */
    fun convertJsonToString(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream("$PATH$fileName")
        val source = inputStream?.let { inputStream.source().buffer() }
        return source!!.readString(StandardCharsets.UTF_8)
    }


}

inline fun <reified T : Any> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}