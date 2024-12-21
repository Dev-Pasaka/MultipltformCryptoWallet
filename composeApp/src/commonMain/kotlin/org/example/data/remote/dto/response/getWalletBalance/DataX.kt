package org.example.data.remote.dto.response.getWalletBalance

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject

@Serializable
data class DataX(
    @Serializable(with = TokenBalancesSerializer::class)
    val tokenBalances: List<TokenBalance> = emptyList()
)

object TokenBalancesSerializer : KSerializer<List<TokenBalance>> {
    override val descriptor: SerialDescriptor =
        ListSerializer(TokenBalance.serializer()).descriptor

    override fun deserialize(decoder: Decoder): List<TokenBalance> {
        val input = decoder as JsonDecoder
        val element = input.decodeJsonElement()

        return when (element) {
            is JsonArray -> element.map {
                input.json.decodeFromJsonElement(TokenBalance.serializer(), it)
            }
            is JsonObject -> emptyList() // Handle empty object as an empty list
            else -> throw SerializationException("Unexpected JSON for tokenBalances: $element")
        }
    }

    override fun serialize(encoder: Encoder, value: List<TokenBalance>) {
        encoder.encodeSerializableValue(
            ListSerializer(TokenBalance.serializer()),
            value
        )
    }
}
