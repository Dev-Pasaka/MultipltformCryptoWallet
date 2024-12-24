package org.example.data.repository

import com.mongodb.client.model.Filters
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import org.example.common.utils.MongoDBConfig
import org.example.domain.entries.RequestLink
import org.example.domain.repository.RequestLinkRepository
import org.example.presentation.dto.response.CreateRequestLinkRes
import org.example.presentation.dto.response.GetRequestLinkRes

class RequestLinkRepositoryImpl(
    private val db: MongoDBConfig,
) : RequestLinkRepository {
    private val requestLinkCollection = db.database.getCollection<RequestLink>("request_link")
    override suspend fun createRequestLink(requestLink: RequestLink): CreateRequestLinkRes =
        withContext(Dispatchers.IO) {
            val createRequestLink = requestLinkCollection.insertOne(requestLink).wasAcknowledged()
            return@withContext if (createRequestLink){
                CreateRequestLinkRes(
                    status = true,
                    shortCode = requestLink.shortCode,
                    message = "Request link created successfully"
                )
            }else{
                CreateRequestLinkRes(
                    status = false,
                    shortCode = "",
                    message = "Failed to create request link"
                )
            }


        }

    override suspend fun getRequestLink(shortCode: String): GetRequestLinkRes  = withContext(
        Dispatchers.IO){
        val getRequestLink = requestLinkCollection.find(
            Filters.eq(RequestLink::shortCode.name, shortCode)
        ).firstOrNull() ?: return@withContext GetRequestLinkRes(
            httpStatusCode = HttpStatusCode.NotFound.value,
            status = false,
            message = "Request link not found"
        )
        return@withContext GetRequestLinkRes(
            status = true,
            message = "Request link retrieved successfully",
            data = getRequestLink.toRequestLinkData()
        )

    }
}