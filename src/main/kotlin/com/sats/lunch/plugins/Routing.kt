package com.sats.lunch.plugins

import com.pspdfkit.api.PdfDocument
import com.pspdfkit.api.providers.FileDataProvider
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.imageio.ImageIO

val httpClient = HttpClient()

fun Application.configureRouting() {
    routing {
        post("/today") {
            val menuUrl = "http://portal.ny28.no/wp-content/uploads/meny28.pdf"
            val menuPdf = httpClient.get(menuUrl)
            val fileBytes = menuPdf.readBytes()

            val response = withContext(Dispatchers.IO) {
                val file = File.createTempFile("lunch", "pdf")
                val outputStream = FileOutputStream(file)

                outputStream.write(fileBytes)

                val document = PdfDocument.open(FileDataProvider(file))
                val page = document.getPage(0)
                val bitmap = page.renderPage()

                val bitmapFile = File.createTempFile("response", "png")
                ImageIO.write(bitmap, "png", bitmapFile)

                return@withContext bitmapFile
            }

            call.respondBytes(response.readBytes(), contentType = ContentType.Image.PNG)
        }
    }
}
