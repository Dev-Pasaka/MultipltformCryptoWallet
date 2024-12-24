package org.example.presentation.dto.response

import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import kotlinx.html.*

suspend fun ApplicationCall.cryptoPaymentError(
    errorMessage: String
) {
    respondHtml {
        head {
            meta { charset = "UTF-8" }
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            title { +"Error - Crypto Payment Request" }
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
                        .error-message {
                            text-align: center;
                            margin-bottom: 20px;
                            font-size: 18px;
                            color: var(--primary);
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
                        .back-link {
                            text-align: center;
                            margin-top: 20px;
                        }
                        .back-link a {
                            text-decoration: none;
                            color: var(--background);
                            font-size: 16px;
                            background-color: var(--primary);
                            padding: 10px 15px;
                            border-radius: 5px;
                            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
                            transition: background-color 0.3s;
                        }
                        .back-link a:hover {
                            background-color: var(--tertiary);
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
                    h1 { +"Error - Crypto Payment Request" }
                }
                div("error-message") {
                    +"Something went wrong!"
                }
                div("details") {
                    p { strong { +"Error:" }; +" $errorMessage" }
                }
                div("back-link") {
                    a(href = "/") { // Link to go back to home or previous page
                        +"Go Back to Home"
                    }
                }
                br
                div("footer") {
                    +"Please try again later or contact support if the issue persists."
                }
            }
        }
    }
}
