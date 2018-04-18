package com.ymlion.parser

import java.io.InputStream
import java.nio.charset.Charset

/**
 * 一般前两个字节各自表示长度，当为128时，则多出一字节修正
 *
 * Created by YMlion on 2018/4/18.
 */
class StringPoolString {
    companion object {
        /**
         * @param flags 字符串编码格式
         */
        fun parse(input: InputStream, flags: Int): StringPoolString {
            val string = StringPoolString()
            string.len = input.read()
            if (string.len >= 128) {
                input.read()
            }
            string.len = input.read()
            if (string.len == 128) {
                string.len = input.read()
            } else if (string.len > 128) {
                string.len = input.read() or ((string.len and 0x7f) shl 8)
            }
            val bytes = ByteArray(string.len)
            input.read(bytes)
            input.read()
            val charset = if (flags >= 0x100) {
                "UTF-8"
            } else {
                input.read()
                "UTF-16"
            }
            string.content = String(bytes, Charset.forName(charset))
            return string
        }
    }

    /**
     * string length, 1 byte
     */
    var len = 0
    /**
     * string content, length bytes
     */
    lateinit var content: String
    /**
     * 1 byte or 2 bytes
     */
    var endMark = 0

    override fun toString(): String {
        return content
    }
}