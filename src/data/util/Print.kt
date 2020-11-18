package com.richmeat.data.util

import java.awt.GraphicsEnvironment
import java.io.FileInputStream
import javax.print.*
import javax.print.attribute.DocAttributeSet
import javax.print.attribute.HashDocAttributeSet
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.PrintRequestAttributeSet

object Print {
    @JvmStatic
    fun main(args: Array<String>){
        printDoc("D://ok.html")
    }
    fun printDoc(doc: String) {


        var pras: PrintRequestAttributeSet = HashPrintRequestAttributeSet()
        var flavor: DocFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE
        var printService = PrintServiceLookup.lookupPrintServices(flavor, pras)
        var defaultService = PrintServiceLookup.lookupDefaultPrintService()
        var service = ServiceUI.printDialog(
            GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.defaultConfiguration, 200, 200,
            printService, defaultService, flavor, pras
        )

        val job = service.createPrintJob()
        val fis = FileInputStream(doc)
        val das: DocAttributeSet = HashDocAttributeSet()
        val document: Doc = SimpleDoc(fis, flavor, das)
        try {
            job.print(document, pras)
        } catch (e: PrintException) {
            e.printStackTrace()
        }
    }

}