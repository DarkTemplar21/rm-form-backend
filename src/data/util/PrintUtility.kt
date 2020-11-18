package com.richmeat.data.util

import java.awt.print.PrinterJob
import javax.print.PrintService

object PrintUtility {
    /**
     * Retrieve a Print Service with a name containing the specified PrinterName; will return null if not found.
     *
     * @return
     */


    fun findPrintService(printerName: String): PrintService? {
        var printerName = printerName
        printerName = printerName.toLowerCase()
        var service: PrintService? = null

        // Get array of all print services
        val services = PrinterJob.lookupPrintServices()

        // Retrieve a print service from the array
        var index = 0
        while (service == null && index < services.size) {
            if (services[index].name.toLowerCase().indexOf(printerName) >= 0) {
                service = services[index]
            }
            index++
        }

        // Return the print service
        return service
    }// get list of all print services

    /**
     * Retrieves a List of Printer Service Names.
     *
     * @return List
     */
    val printerServiceNameList: List<String>
        get() {

            // get list of all print services
            val services = PrinterJob.lookupPrintServices()
            val list = mutableListOf<String>()
            for (i in services.indices) {
                list.add(services[i].name)
            }
            return list
        }

}