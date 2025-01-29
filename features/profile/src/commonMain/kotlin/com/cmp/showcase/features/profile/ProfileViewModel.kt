package com.cmp.showcase.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmp.showcase.data.profile.Education
import com.cmp.showcase.data.profile.Experience
import com.cmp.showcase.data.profile.Language
import com.cmp.showcase.data.profile.Profile
import com.cmp.showcase.data.profile.ProfileRepository
import com.cmp.showcase.data.profile.Project
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewmodel(private val profileRepository: ProfileRepository): ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Exception handled: ${throwable.message}")
        _state.value = _state.value.copy(uiState = UiState.Error(throwable.message ?: "Unknown Error"))
    }

    private val context = Dispatchers.Default + exceptionHandler

    init {
        loadData()
    }

    private fun loadData() {
        _state.value = _state.value.copy(uiState = UiState.Loading)
        viewModelScope.launch(context) {
            val profile = profileRepository.getProfile()
            _state.value = _state.value.copy(
                profile = profile
            )
            loadExperiences()
            loadEducation()
            loadProjects()
            loadLanguages()
            _state.value = _state.value.copy(uiState = UiState.Success)
        }
    }

    private fun loadExperiences() {
        viewModelScope.launch(context) {
            val experiences = profileRepository.getExperiences()
            _state.value = _state.value.copy(
                experiences = experiences
            )
        }
    }

    private fun loadProjects() {
        viewModelScope.launch(context) {
            val projects = profileRepository.getProjects()
            _state.value = _state.value.copy(
                projects = projects
            )
        }
    }

    private fun loadEducation() {
        viewModelScope.launch(context) {
            val education = profileRepository.getEducations()
            _state.value = _state.value.copy(
                educations = education
            )
        }
    }

    private fun loadLanguages() {
        viewModelScope.launch(context) {
            val languages = profileRepository.getLanguages()
            _state.value = _state.value.copy(
                languages = languages
            )
        }
    }
}


data class ProfileState (
    val profile: Profile? = null,
    val experiences: List<Experience> = emptyList(),
    val educations: List<Education> = emptyList(),
    val projects: List<Project> = emptyList(),
    val languages: List<Language> = emptyList(),
    val uiState: UiState = UiState.Loading
)

sealed interface UiState {
    data object Loading: UiState
    data object Success: UiState
    data class Error(val msg: String): UiState
}