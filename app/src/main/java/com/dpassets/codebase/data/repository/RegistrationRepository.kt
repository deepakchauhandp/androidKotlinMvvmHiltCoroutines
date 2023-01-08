package com.dpassets.codebase.data.repository

import com.dpassets.codebase.constants.EventTask
import com.dpassets.codebase.constants.Status
import com.dpassets.codebase.constants.Task
import com.dpassets.codebase.data.models.RegisterUserResponseModel
import com.dpassets.codebase.data.models.user.register_user.request.RegisterUserRequestModel
import com.dpassets.codebase.data.network.ApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class RegistrationRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun registerUser(registerUserRequestModel: RegisterUserRequestModel): Flow<EventTask<RegisterUserResponseModel>> = flow {
        emit(EventTask.Loading(Task.REGISTER_USER))
        try {
            val response = apiService.registerUser(registerUserRequestModel)
            emit(EventTask.Success(response, Task.REGISTER_USER))
        } catch (e: Exception) {
            emit(EventTask.Error(e.toString(), Status.ERROR, Task.REGISTER_USER))
        }
    }
}
