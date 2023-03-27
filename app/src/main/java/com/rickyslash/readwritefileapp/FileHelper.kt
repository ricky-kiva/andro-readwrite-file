package com.rickyslash.readwritefileapp

import android.content.Context

internal object FileHelper {

    fun writeToFile(fileModel: FileModel, context: Context) {
        // openFileOutput() returns 'FileOutputStream' that could be used to write data to file
        // it takes 'filename' as parameter & the 'file cretion mode' (MODE_PRIVATE)
        // 'use' will 'execute function' in 'this resource', then 'ensure that it's closed' with or without exception
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
            // writes 'data' property from 'fileModel' to file. Also converts it to 'ByteArray()'
            it.write(fileModel.data?.toByteArray())
        }
    }

    fun readFromFile(context: Context, filename: String): FileModel {
        // initiate 'fileModel' data class
        val fileModel = FileModel()
        // set 'filename' property in 'fileModel' object
        fileModel.filename = filename
        // 'openFileInput' will get the 'file' based on 'filename' within 'context'
        // 'bufferedReader()' enable to read context of the file as text
        // useLines() returns 'iterable line' from 'Buffered Reader'
        // those 'line' is 'iterated' using 'lines.fold()'
        // lines.fold() works like, line will be 'accumulated each iteration', and 'concatenated' with line that is on the 'available lines'
        fileModel.data = context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.fold("") { accumulator, text ->
                "$accumulator $text"
            }
        }
        // return the 'data class' object
        return fileModel
    }

}