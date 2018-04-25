package com.ymlion.parser

import java.io.InputStream
import java.io.RandomAccessFile

/**
 * table header, 12 bytes
 *
 * Created by YMlion on 2018/4/17.
 */
class ResTableHeader() {

    constructor(file: RandomAccessFile) : this() {
        header = ResHeader(file)
        val byteArray = ByteArray(4)
        file.read(byteArray)
        packageCount = ByteUtil.bytes2Int(byteArray, 0, 4)
    }

    companion object {
        public fun parse(inputStream: InputStream): ResTableHeader {
            val resTableHeader = ResTableHeader()
            resTableHeader.header = ResHeader.parse(inputStream)
            val byteArray = ByteArray(4)
            inputStream.read(byteArray)
            resTableHeader.packageCount = ByteUtil.bytes2Int(byteArray, 0, 4)
            println(resTableHeader.toString())
            return resTableHeader
        }
    }

    /**
     * chunk header, 8 bytes
     */
    lateinit var header: ResHeader
    /**
     * package count, 4 bytes, default is 1
     */
    var packageCount = 1

    override fun toString(): String {
        return StringBuilder().append("ResTableHeader:").append('\n').append(
                        header.toString()).append('\n').append(
                        "package count     : $packageCount").toString()
    }
}