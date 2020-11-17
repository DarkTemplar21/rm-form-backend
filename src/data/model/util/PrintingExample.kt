package com.richmeat.data.model.util

import java.awt.print.PrinterJob
import java.io.File
import javax.print.PrintService
import javax.print.PrintServiceLookup
import org.apache.pdfbox.pdmodel.PDDocument;


object PrintingUtil {
    @Throws(Exception::class)
    fun printPDF(pdfDocument: File) {
        val document: PDDocument = PDDocument.load(pdfDocument)
        val myPrintService: PrintService? =
            findPrintService("\\\\RICHMEAT15-PC.RICHMEATCUBA.LOCAL\\Canon G6000 series")
        val job: PrinterJob = PrinterJob.getPrinterJob()
        job.setPageable((document))
        job.printService = myPrintService
        job.print()
    }

    fun getPrinterList(): MutableList<String>{
        val printServices: Array<PrintService> = PrintServiceLookup.lookupPrintServices(null, null)
        val printersName = mutableListOf<String>()
        printServices.forEach {
            printersName.add(it.name)
        }
        return printersName
    }

    private fun findPrintService(printerName: String): PrintService? {
        val printServices: Array<PrintService> = PrintServiceLookup.lookupPrintServices(null, null)
        println("asd:"+printServices[10].name)
        println("asd:"+printServices[11].name)
        println("asd:"+printServices[12].name)
        println("asd:"+printServices[13].name)
        println("asd:"+printServices[14].name)
        println("asd:"+printServices[15].name)
        println("asd:"+printServices[16].name)
        for (printService in printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService
            }
        }
        return null
    }

}