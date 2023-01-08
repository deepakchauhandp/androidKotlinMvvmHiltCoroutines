package com.dpassets.codebase.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpassets.codebase.constants.EventTask
import com.dpassets.codebase.data.models.user.register_user.request.RegisterUserRequestModel
import com.dpassets.codebase.data.repository.RegistrationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: RegistrationRepository
) : ViewModel() {

    private val _response: MutableLiveData<EventTask<*>> = MutableLiveData()
    val response: LiveData<EventTask<*>> = _response
    private var job: Job? = null

    fun registerUser(registerUserRequestModel: RegisterUserRequestModel) {
        job = viewModelScope.launch {
            repository.registerUser(registerUserRequestModel).collect { values ->
                _response.value = values
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
