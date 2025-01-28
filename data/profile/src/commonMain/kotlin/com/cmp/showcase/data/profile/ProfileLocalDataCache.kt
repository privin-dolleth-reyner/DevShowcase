package com.cmp.showcase.data.profile

object ProfileLocalDataCache {
    var profile: Profile? = null
    var experiences = emptyList<Experience>()
    var education = emptyList<Education>()
    var projects = emptyList<Project>()
}