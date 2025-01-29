package com.cmp.showcase.data.profile

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import kotlinx.serialization.Serializable


interface ProfileRepository {
    suspend fun getProfile(): Profile
    suspend fun getExperiences(): List<Experience>
    suspend fun getEducations(): List<Education>
    suspend fun getProjects(): List<Project>
    suspend fun getLanguages(): List<Language>
}

class ProfileRepositoryImpl : ProfileRepository {
    private val firestore = Firebase.firestore

    private val profile = firestore.collection("Profile").document("profile")
    private val education = profile.collection("Education")
    private val experience = profile.collection("Experience")
    private val projects = profile.collection("Projects")
    private val languages = profile.collection("Languages")

    override suspend fun getProfile(): Profile {
        if (ProfileLocalDataCache.profile != null) return ProfileLocalDataCache.profile!!

        return profile.get().data<Profile>().also {
            ProfileLocalDataCache.profile = it
        }
    }

    override suspend fun getExperiences(): List<Experience> {
        if (ProfileLocalDataCache.experiences.isNotEmpty()) return ProfileLocalDataCache.experiences

        return experience.get().documents.map<DocumentSnapshot, Experience> { it.data() }
            .filter { it.displayPriority > 0 }
            .sortedBy { it.displayPriority }
            .also {
                ProfileLocalDataCache.experiences = it
            }
    }

    override suspend fun getEducations(): List<Education> {
        if (ProfileLocalDataCache.education.isNotEmpty()) return ProfileLocalDataCache.education

        return education.get().documents.map<DocumentSnapshot, Education> { it.data() }
            .filter { it.displayPriority > 0 }
            .sortedBy { it.displayPriority }
            .also {
                ProfileLocalDataCache.education = it
            }
    }

    override suspend fun getProjects(): List<Project> {
        if (ProfileLocalDataCache.projects.isNotEmpty()) return ProfileLocalDataCache.projects

        return projects.get().documents.map<DocumentSnapshot, Project> { it.data() }
            .filter { it.displayPriority > 0 }
            .sortedBy { it.displayPriority }
            .also {
                ProfileLocalDataCache.projects = it
            }
    }

    override suspend fun getLanguages(): List<Language> {
        if (ProfileLocalDataCache.languages.isNotEmpty()) return ProfileLocalDataCache.languages

        return languages.get().documents.map<DocumentSnapshot, Language> { it.data() }
            .filter { it.displayPriority > 0 }
            .sortedBy { it.displayPriority }
            .also {
                ProfileLocalDataCache.languages = it
            }
    }

}

@Serializable
data class Profile(
    val firstName: String,
    val lastName: String,
    val designation: String,
    val about: String,
    val location: String,
    val skills: List<String>,
    val emailAddress: String,
    val linkedInUrl: String,
    val githubUrl: String
) {
    fun getName() = "$firstName $lastName"
}


@Serializable
data class Experience(
    val displayPriority: Int,
    val designation: String,
    val company: String,
    val location: String,
    val duration: String,
    val description: String
)

@Serializable
data class Education(
    val displayPriority: Int,
    val degree: String,
    val institution: String,
    val location: String,
    val duration: String
)

@Serializable
data class Project(
    val displayPriority: Int,
    val title: String,
    val description: String,
    val link: String
)

@Serializable
data class Language(
    val displayPriority: Int,
    val name: String,
)