package org.example.domain.usecase.requestLink

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.common.Resource
import org.example.domain.model.RequestLinkData
import org.example.domain.repository.RequestLinkRepository

class GetRequestLinkDataUseCase(
    private val repository: RequestLinkRepository
) {
    operator fun invoke(shortCode: String): Flow<Resource<RequestLinkData>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getRequestLink(shortCode)
            if (response.status){
                emit(Resource.Success(data = response.toRequestLinkData(), message = response.message))
            }else{
                emit(Resource.Error(response.message))
            }
        }catch (e:Exception){
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }

    }
}
