@file:JvmName("Log")
package android.util

/**
 * Mock `android.util.Log.e` for unit tests
 */
fun e(tag: String, msg: String): Int {
    println("ERROR: $tag: $msg")
    return 0
}
