pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String
        val sqldelightVersion = extra["sqldelight.version"] as String
        val libresVersion = extra["libres.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.compose").version(composeVersion)
        id("app.cash.sqldelight").version(sqldelightVersion)
        id("io.github.skeptick.libres").version(libresVersion)
    }
}

rootProject.name = "Bartender"

include(":androidApp")
include(":shared")