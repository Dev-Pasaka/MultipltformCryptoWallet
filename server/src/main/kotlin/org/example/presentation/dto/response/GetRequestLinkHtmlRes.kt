import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import kotlinx.html.*
import org.example.domain.usecases.requestLink.GenerateQrCodeUseCase
import org.example.presentation.dto.response.RequestLinkData
import org.koin.ktor.ext.inject

suspend fun ApplicationCall.cryptoPaymentRequest(
    id: String,
    address: String,
    blockchain: String,
    amount: Double,
    qrData: RequestLinkData
) {
    val qrGenerator by inject<GenerateQrCodeUseCase>()
    val qrCodeUrl = qrGenerator.invoke(qrData)
    respondHtml {
        head {
            meta { charset = "UTF-8" }
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            title { +"Crypto Payment Request" }
            style {
                unsafe {
                    raw("""
                        :root {
                            --primary: #FFBE44;
                            --secondary: #886034;
                            --tertiary: #D3CBBF;
                            --neutral: #96938E;
                            --background: #121319;
                        }
                        body {
                            margin: 0;
                            font-family: 'Arial', sans-serif;
                            background-color: var(--background);
                            color: var(--tertiary);
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                        }
                        .container {
                            max-width: 500px;
                            width: 100%;
                            padding: 20px;
                            border-radius: 10px;
                            background-color: var(--secondary);
                            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5);
                        }
                        .header {
                            text-align: center;
                            margin-bottom: 20px;
                        }
                        .header h1 {
                            color: var(--primary);
                            font-size: 24px;
                            margin: 0;
                        }
                        .message {
                            text-align: center;
                            margin-bottom: 20px;
                            font-size: 18px;
                            color: var(--tertiary);
                        }
                        .details {
                            padding: 15px;
                            background-color: var(--tertiary);
                            color: var(--background);
                            border-radius: 8px;
                            margin-bottom: 20px;
                        }
                        .details p {
                            margin: 5px 0;
                        }
                        .qr-code {
                            text-align: center;
                            margin-bottom: 20px;
                        }
                        .qr-code img {
                            width: 300px;
                            height: 300px;
                            border: 5px solid var(--primary);
                            border-radius: 10px;
                        }
                        .download {
                            text-align: center;
                            margin-top: 10px;
                        }
                        .download a {
                            text-decoration: none;
                            color: var(--background );
                            font-size: 16px;
                            background-color: var(--tertiary);
                            padding: 10px 15px;
                            border-radius: 5px;
                            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
                            transition: background-color 0.3s;
                        }
                        .download a:hover {
                            background-color: var(--primary);
                            color: var(--background);
                        }
                        .footer {
                            text-align: center;
                            font-size: 14px;
                            color: var(--neutral);
                        }
                    """.trimIndent())
                }
            }
        }
        body {
            div("container") {
                div("header") {
                    h1 { +"Crypto Payment Request" }
                }
                div("message") {
                    +"Request link retrieved successfully"
                }
                div("details") {
                    p { strong { +"ID:" }; +" $id" }
                    p { strong { +"Address:" }; +" $address" }
                    p { strong { +"Blockchain:" }; +" $blockchain" }
                    p { strong { +"Amount:" }; +" $amount ETH" }
                }
                div("qr-code") {
                    img(src = qrCodeUrl, alt = "QR Code") {
                        attributes["id"] = "qr-code"
                    }
                }
                div("download") {
                    a(href = qrCodeUrl) {
                        attributes["id"] = "download-link"
                        attributes["download"] = "qr-code.png"
                        +"Download QR Code"
                    }
                }

                br {}
                div("footer") {
                    +"Please send the exact amount to the address provided."
                }
            }
        }
    }
}

