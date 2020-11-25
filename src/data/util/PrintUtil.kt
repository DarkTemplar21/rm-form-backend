package com.richmeat.data.util

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.richmeat.data.form.Form
import io.ktor.utils.io.charsets.Charset
import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import org.joda.time.DateTime
import org.jsoup.Jsoup
import java.awt.print.PrinterJob
import java.io.*
import java.net.URL
import javax.print.PrintService
import javax.print.PrintServiceLookup


object PrintingUtil {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val file = File("src/assets/temp_form.html")
        val form = Form(
            DateTime(),
            DateTime(),
            "ok",
            "pepe",
            "juan",
            mutableListOf()
        )
        val inputStream = file.inputStream()
        val doc = Jsoup.parse(inputStream, "UTF-8", "http://com.richmeat/")


        doc.getElementById("{elaborado}").html("<strong>"+doc.getElementById("{elaborado}").text().replace("{elaborado}",form.created_by)+"</strong>")
        doc.getElementById("{aprobado}").html("<strong>"+form.reviewed_by+"</strong>")
        doc.getElementById("{fecha_elaborado}").html("<strong>"+doc.getElementById("{fecha_elaborado}").text().replace("{fecha_elaborado}",DateHelper.getStringShortDate(form.created_date))+"</strong>")
        doc.getElementById("{fecha_aprobado}").html("<strong>"+doc.getElementById("{fecha_aprobado}").text().replace("{fecha_aprobado}",DateHelper.getStringShortDate(form.created_date))+"</strong>")
        doc.getElementById("{hora}").html("<strong>"+doc.getElementById("{hora}").text().replace("{hora}",DateHelper.getStringHour(form.created_date))+"</strong>")
        doc.getElementById("{dia}").html("<strong>"+doc.getElementById("{dia}").text().replace("{dia}",DateHelper.getStringDay(form.created_date))+"</strong>")
        doc.getElementById("{operario}").html("<strong>"+doc.getElementById("{operario}").text().replace("{operario}","FALTA")+"</strong>")
        doc.getElementById("{supervisor}").html("<strong>"+doc.getElementById("{supervisor}").text().replace("{supervisor}","FALTA")+"</strong>")
        doc.getElementById("{observaciones}").html("<strong>"+doc.getElementById("{observaciones}").text().replace("{observaciones}","FALTA")+"</strong>")



        val html = doc.toString()
        saveHtmlFile(html)
        printPDF(html2Pdf(File("src/assets/tempHtml.html")))

    }





        fun printPDF(pdfDocument: File) {
            val document: PDDocument = PDDocument.load(pdfDocument)
             document.toString()
        val myPrintService: PrintService? =
            findPrintService("\\\\RICHMEAT15-PC.RICHMEATCUBA.LOCAL\\Canon G6000 series")
        val job: PrinterJob = PrinterJob.getPrinterJob()
        job.setPageable((document))
        job.printService = myPrintService
        job.print()
        }
    }

    fun getPrinterList(): MutableList<String> {
        val printServices: Array<PrintService> = PrintServiceLookup.lookupPrintServices(null, null)
        val printersName = mutableListOf<String>()
        printServices.forEach {
            printersName.add(it.name)
        }
        return printersName
    }

    private fun findPrintService(printerName: String): PrintService? {
        val printServices: Array<PrintService> = PrintServiceLookup.lookupPrintServices(null, null)
//        println("asd:" + printServices[10].name)
//        println("asd:" + printServices[11].name)
//        println("asd:" + printServices[12].name)
//        println("asd:" + printServices[13].name)
//        println("asd:" + printServices[14].name)
//        println("asd:" + printServices[15].name)
//        println("asd:" + printServices[16].name)
        for (printService in printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService
            }
        }
        return null
    }

    fun function(pdf_url: String?): String? {
        var pdfStripper: PDFTextStripper? = null
        val pDoc: PDDocument
        val cDoc: COSDocument
        var parsedText: String? = ""
        try {
            val url = URL(pdf_url)
            val file = BufferedInputStream(url.openStream())
            val parser = PDFParser(file)
            parser.parse()
            cDoc = parser.document
            pdfStripper = PDFTextStripper()
            pdfStripper.startPage = 1
            pdfStripper.endPage = 1
            pDoc = PDDocument(cDoc)
            parsedText = pdfStripper.getText(pDoc)
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return parsedText
    }

 fun html2Pdf(html: File):File{

     val pdfDest = File("output.pdf")
     // pdfHTML specific code
     // pdfHTML specific code
     val converterProperties = ConverterProperties()
     HtmlConverter.convertToPdf(
         FileInputStream(html),
         FileOutputStream(pdfDest), converterProperties
     )
     return pdfDest
 }
  fun saveHtmlFile(html: String) {
    val path: String = "src/assets/"
    var fileName: String = "tempHtml"

    fileName = "$fileName.html"
    val file = File(path, fileName)

    try {
        val out = FileOutputStream(file,false)
        val data = html.toByteArray()
        out.write(data)
        out.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}




