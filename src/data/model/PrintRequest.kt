package com.richmeat.data.model

data class PrintRequest(
    val printerName: String,
    val numberOfCopies: Int,
    val formId: Int
)