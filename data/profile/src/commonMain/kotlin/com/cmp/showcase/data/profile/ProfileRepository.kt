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
}

class ProfileRepositoryImpl: ProfileRepository{
    private val firestore = Firebase.firestore

    private val profile = firestore.collection("Profile").document("profile")
    private val education = profile.collection("Education")
    private val experience = profile.collection("Experience")
    private val projects = profile.collection("Projects")

    override suspend fun getProfile(): Profile {
        if(ProfileLocalDataCache.profile != null) return ProfileLocalDataCache.profile!!

        return profile.get().data<Profile>().also {
            ProfileLocalDataCache.profile = it
        }
    }

    override suspend fun getExperiences(): List<Experience> {
        if(ProfileLocalDataCache.experiences.isNotEmpty()) return ProfileLocalDataCache.experiences

        return experience.get().documents.map<DocumentSnapshot, Experience> { it.data() }.also {
            ProfileLocalDataCache.experiences = it
        }
    }

    override suspend fun getEducations(): List<Education> {
        if(ProfileLocalDataCache.education.isNotEmpty()) return ProfileLocalDataCache.education

        return education.get().documents.map<DocumentSnapshot, Education> { it.data() }.also {
            ProfileLocalDataCache.education = it
        }
    }

    override suspend fun getProjects(): List<Project> {
        if(ProfileLocalDataCache.projects.isNotEmpty()) return ProfileLocalDataCache.projects

        return projects.get().documents.map<DocumentSnapshot, Project> { it.data() }.also {
            ProfileLocalDataCache.projects = it
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
){
    fun getName() = "$firstName $lastName"
}


@Serializable
data class Experience(
    val designation: String,
    val company: String,
    val location: String,
    val duration: String,
    val description: String
)

@Serializable
data class Education(
    val degree: String,
    val institution: String,
    val location: String,
    val duration: String
)

@Serializable
data class Project(
    val title: String,
    val description: String,
    val link: String
)