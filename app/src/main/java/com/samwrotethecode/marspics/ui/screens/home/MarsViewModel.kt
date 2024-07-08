package com.samwrotethecode.marspics.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samwrotethecode.marspics.MarsPicsApplication
import com.samwrotethecode.marspics.data.MarsPicsRepository
import com.samwrotethecode.marspics.domain.MarsPics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val marsPics: List<MarsPics>) : MarsUiState
    data object Error : MarsUiState
    data object Loading : MarsUiState
}

class MarsViewModel(private val marsPicsRepository: MarsPicsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<MarsUiState>(MarsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getMarsPics()
    }

    private fun getMarsPics() = viewModelScope.launch {
        try {
            val pics = marsPicsRepository.getMarsPics()
            _uiState.update { MarsUiState.Success(pics) }
        } catch (e: IOException) {
            _uiState.update { MarsUiState.Error }
        }
    }


    fun retry() {
        _uiState.update { MarsUiState.Loading }
        getMarsPics()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPicsApplication)
                val marsPicsRepository = application.container.marsPicsRepository
                MarsViewModel(marsPicsRepository = marsPicsRepository)
            }
        }
    }
}