package org.example.domain.usecase.requestLink

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.common.Resource
import org.example.data.remote.dto.request.CreateRequestLinkReq
import org.example.domain.model.RequestLinkData
import org.example.domain.repository.RequestLinkRepository

class CreateRequestLinkUseCase(
    private val repository: RequestLinkRepository
) {
    operator fun invoke(body: CreateRequestLinkReq): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.createRequestLink(body)
            if (response.status){
                emit(Resource.Success(data = response.toLink(), message = response.message))
            }else{
                emit(Resource.Error(response.message))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}