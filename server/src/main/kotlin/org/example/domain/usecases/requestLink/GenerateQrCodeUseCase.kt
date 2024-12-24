package org.example.domain.usecases.requestLink

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.example.presentation.dto.response.RequestLinkData
import qrcode.QRCode
import qrcode.color.Colors
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File

import java.util.Base64
import javax.imageio.ImageIO

class GenerateQrCodeUseCase {

    operator fun invoke(content: RequestLinkData): String {
        // Convert the content to JSON string
        val json = Json.encodeToString(
            serializer = MapSerializer(String.serializer(), String.serializer()),
            value = mapOf("address" to content.address, "amount" to content.amount.toString())
        )


        val logoPath = when(content.blockchain){
            "ETH-SEPOLIA" -> "/eth-icon.png"
            "MATIC-AMOY" -> "/matic-icon.png"
            "SOL-DEVNET" -> "/sol-icon.png"
            else -> "/bitcoin.png"

        }
        val logoBytes = loadLogoAsByteArray(logoPath = logoPath, width = 300, height = 300)

        // Create the QR code with the logo
        val qrCode = QRCode
            .ofRoundedSquares()
            .withColor(0xFF886034.toInt())
            .withLogo(
                logo = logoBytes,
                width = 300,
                height = 300,
                clearLogoArea = false
            )
            .withSize(25) // Customize size
            .build(json)

        // Render the QR code to PNG
        val pngBytes = qrCode.render()

        // Convert PNG bytes to Base64 string
        val outputStream = ByteArrayOutputStream()
        outputStream.write(pngBytes.getBytes())
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray())
    }

    private fun loadLogoAsByteArray(logoPath: String, width: Int, height: Int): ByteArray {
        // Get the resource from the class loader
        val inputStream = this::class.java.getResourceAsStream(logoPath)
            ?: throw IllegalArgumentException("Logo file not found: $logoPath")

        // Read the input stream into a BufferedImage
        val image: BufferedImage = ImageIO.read(inputStream)

        // Resize the image to the specified width and height
        val resizedImage = resizeImage(image, width, height)

        // Add a circular white background with padding to the resized logo
        val circularLogo = addCircularBackground(resizedImage, width + 4) // Add padding of 2 pixels on all sides

        // Write the final image to ByteArrayOutputStream as PNG
        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(circularLogo, "PNG", byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    // Helper function to resize the image
    private fun resizeImage(image: BufferedImage, width: Int, height: Int): BufferedImage {
        val resizedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val graphics: Graphics2D = resizedImage.createGraphics()
        graphics.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null)
        graphics.dispose()
        return resizedImage
    }

    // Helper function to add a circular white background with padding
    private fun addCircularBackground(logo: BufferedImage, size: Int): BufferedImage {
        // Create a new BufferedImage with a white circular background
        val circularImage = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
        val graphics: Graphics2D = circularImage.createGraphics()

        // Fill the background with white color
        graphics.color = Color.WHITE
        graphics.fillOval(0, 0, size, size)

        // Calculate the position to center the logo
        val padding = 2  // Padding around the circular border
        val logoX = (size - logo.width) / 2
        val logoY = (size - logo.height) / 2

        // Draw the resized logo in the center
        graphics.drawImage(logo, logoX, logoY, null)
        graphics.dispose()

        return circularImage
    }
}