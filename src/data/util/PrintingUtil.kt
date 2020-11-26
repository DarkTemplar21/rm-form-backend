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
   public fun main(form: FormDTO,numberOfCopies: Int) {
        val file = File("src/assets/temp_form.html")
        val form = Form(
            "",
            "",
            "ok",
            "pepe",
            "juan",
            mutableListOf()
        )
        val inputStream = file.inputStream()
        val doc = Jsoup.parse(inputStream, "UTF-8", "http://com.richmeat/")


        doc.getElementById("{elaborado}").html(
            "<strong>" + doc.getElementById("{elaborado}").text().replace("{elaborado}", form.created_by) + "</strong>"
        )
        doc.getElementById("{aprobado}").html("<strong>" + form.reviewed_by + "</strong>")
        doc.getElementById("{fecha_elaborado}").html(
            "<strong>" + doc.getElementById("{fecha_elaborado}").text()
                .replace("{fecha_elaborado}", DateHelper.getStringShortDate(form.created_date)) + "</strong>"
        )
        doc.getElementById("{fecha_aprobado}").html(
            "<strong>" + doc.getElementById("{fecha_aprobado}").text()
                .replace("{fecha_aprobado}", DateHelper.getStringShortDate(form.created_date)) + "</strong>"
        )
        doc.getElementById("{hora}").html(
            "<strong>" + doc.getElementById("{hora}").text()
                .replace("{hora}", DateHelper.getStringHour(form.created_date)) + "</strong>"
        )
        doc.getElementById("{dia}").html(
            "<strong>" + doc.getElementById("{dia}").text()
                .replace("{dia}", DateHelper.getStringDay(form.created_date)) + "</strong>"
        )
        doc.getElementById("{operario}")
            .html("<strong>" + doc.getElementById("{operario}").text().replace("{operario}", "FALTA") + "</strong>")
        doc.getElementById("{supervisor}")
            .html("<strong>" + doc.getElementById("{supervisor}").text().replace("{supervisor}", "FALTA") + "</strong>")
        doc.getElementById("{observaciones}").html(
            "<strong>" + doc.getElementById("{observaciones}").text().replace("{observaciones}", "FALTA") + "</strong>"
        )
//        doc.getElementById("{1es}").text(TempFormHelper.intToString(form.anden_1y2_on, true))
//        doc.getElementById("{2es}").text(TempFormHelper.intToString(form.conservacion_mp_on, true))
//        doc.getElementById("{3es}").text(TempFormHelper.intToString(form.conservacion_pt_on, true))
//        doc.getElementById("{4es}").text(TempFormHelper.intToString(form.anden_3y4_on, true))
//        doc.getElementById("{5es}").text(TempFormHelper.intToString(form.pasillo_on, true))
//        doc.getElementById("{6es}").text(TempFormHelper.intToString(form.empaque_on, true))
//        doc.getElementById("{7es}").text(TempFormHelper.intToString(form.preenfriamiento_pt_on, true))
//        doc.getElementById("{8es}").text(TempFormHelper.intToString(form.proceso_on, true))
//        doc.getElementById("{9es}").text(TempFormHelper.intToString(form.atemperado_mp_on, true))
//        doc.getElementById("{1en}").text(TempFormHelper.intToString(form.anden_1y2_on, false))
//        doc.getElementById("{2en}").text(TempFormHelper.intToString(form.conservacion_mp_on, false))
//        doc.getElementById("{3en}").text(TempFormHelper.intToString(form.conservacion_pt_on, false))
//        doc.getElementById("{4en}").text(TempFormHelper.intToString(form.anden_3y4_on, false))
//        doc.getElementById("{5en}").text(TempFormHelper.intToString(form.pasillo_on, false))
//        doc.getElementById("{6en}").text(TempFormHelper.intToString(form.empaque_on, false))
//        doc.getElementById("{7en}").text(TempFormHelper.intToString(form.preenfriamiento_pt_on, false))
//        doc.getElementById("{8en}").text(TempFormHelper.intToString(form.proceso_on, false))
//        doc.getElementById("{9en}").text(TempFormHelper.intToString(form.atemperado_mp_on, false))
//        doc.getElementById("{1ts}").text(TempFormHelper.intToString(form.anden_1y2_in_range, true))
//        doc.getElementById("{2ts}").text(TempFormHelper.intToString(form.conservacion_mp_in_range, true))
//        doc.getElementById("{3ts}").text(TempFormHelper.intToString(form.conservacion_pt_in_range, true))
//        doc.getElementById("{4ts}").text(TempFormHelper.intToString(form.anden_3y4_in_range, true))
//        doc.getElementById("{5ts}").text(TempFormHelper.intToString(form.pasillo_in_range, true))
//        doc.getElementById("{6ts}").text(TempFormHelper.intToString(form.empaque_in_range, true))
//        doc.getElementById("{7ts}").text(TempFormHelper.intToString(form.preenfriamiento_pt_in_range, true))
//        doc.getElementById("{8ts}").text(TempFormHelper.intToString(form.proceso_in_range, true))
//        doc.getElementById("{9ts}").text(TempFormHelper.intToString(form.atemperado_mp_in_range, true))
//        doc.getElementById("{1tn}").text(TempFormHelper.intToString(form.anden_1y2_in_range, false))
//        doc.getElementById("{2tn}").text(TempFormHelper.intToString(form.conservacion_mp_in_range, false))
//        doc.getElementById("{3tn}").text(TempFormHelper.intToString(form.conservacion_pt_in_range, false))
//        doc.getElementById("{4tn}").text(TempFormHelper.intToString(form.anden_3y4_in_range, false))
//        doc.getElementById("{5tn}").text(TempFormHelper.intToString(form.pasillo_in_range, false))
//        doc.getElementById("{6tn}").text(TempFormHelper.intToString(form.empaque_in_range, false))
//        doc.getElementById("{7tn}").text(TempFormHelper.intToString(form.preenfriamiento_pt_in_range, false))
//        doc.getElementById("{8tn}").text(TempFormHelper.intToString(form.proceso_in_range, false))
//        doc.getElementById("{9tn}").text(TempFormHelper.intToString(form.atemperado_mp_in_range, false))
//        doc.getElementById("{1ls}").text(TempFormHelper.intToString(form.anden_1y2_reviewed, true))
//        doc.getElementById("{2ls}").text(TempFormHelper.intToString(form.conservacion_mp_reviewed, true))
//        doc.getElementById("{3ls}").text(TempFormHelper.intToString(form.conservacion_pt_reviewed, true))
//        doc.getElementById("{4ls}").text(TempFormHelper.intToString(form.anden_3y4_reviewed, true))
//        doc.getElementById("{5ls}").text(TempFormHelper.intToString(form.pasillo_reviewed, true))
//        doc.getElementById("{6ls}").text(TempFormHelper.intToString(form.empaque_reviewed, true))
//        doc.getElementById("{7ls}").text(TempFormHelper.intToString(form.preenfriamiento_pt_reviewed, true))
//        doc.getElementById("{8ls}").text(TempFormHelper.intToString(form.proceso_reviewed, true))
//        doc.getElementById("{9ls}").text(TempFormHelper.intToString(form.atemperado_mp_reviewed, true))
//        doc.getElementById("{1ln}").text(TempFormHelper.intToString(form.anden_1y2_reviewed, false))
//        doc.getElementById("{2ln}").text(TempFormHelper.intToString(form.conservacion_mp_reviewed, false))
//        doc.getElementById("{3ln}").text(TempFormHelper.intToString(form.conservacion_pt_reviewed, false))
//        doc.getElementById("{4ln}").text(TempFormHelper.intToString(form.anden_3y4_reviewed, false))
//        doc.getElementById("{5ln}").text(TempFormHelper.intToString(form.pasillo_reviewed, false))
//        doc.getElementById("{6ln}").text(TempFormHelper.intToString(form.empaque_reviewed, false))
//        doc.getElementById("{7ln}").text(TempFormHelper.intToString(form.preenfriamiento_pt_reviewed, false))
//        doc.getElementById("{8ln}").text(TempFormHelper.intToString(form.proceso_reviewed, false))
//        doc.getElementById("{9ln}").text(TempFormHelper.intToString(form.atemperado_mp_reviewed, false))


        val html = doc.toString()
        saveHtmlFile(html)
        printPDF(html2Pdf(File("src/assets/tempHtml.html")))

    }

    fun getPrinterList(): MutableList<String> {
        val printServices: Array<PrintService> = PrintServiceLookup.lookupPrintServices(null, null)
        val printersName = mutableListOf<String>()
        printServices.forEach {
            printersName.add(it.name)
        }
        return printersName
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

fun html2Pdf(html: File): File {

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
        val out = FileOutputStream(file, false)
        val data = html.toByteArray()
        out.write(data)
        out.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}




