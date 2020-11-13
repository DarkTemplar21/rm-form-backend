package data.model.util

import java.awt.print.PrinterJob
import java.io.File
import javax.print.PrintService
import javax.print.PrintServiceLookup
import org.apache.pdfbox.pdmodel.PDDocument;


object PrintingExample {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val document: PDDocument = PDDocument.load(File("C:/temp/example.pdf"))
        val myPrintService: PrintService? = findPrintService("My Windows printer Name")
        val job: PrinterJob = PrinterJob.getPrinterJob()
        job.setPageable((document))
        job.setPrintService(myPrintService)
        job.print()
    }

    private fun findPrintService(printerName: String): PrintService? {
        val printServices: Array<PrintService> = PrintServiceLookup.lookupPrintServices(null, null)
        for (printService in printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService
            }
        }
        return null
    }

}