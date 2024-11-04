pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Education is simple"
include(":app")
include(":core")
include(":core:common")
include(":core:common-impl")
include(":core:presentation")
include(":core:components")
include(":features")
include(":features:tasks")
include(":data")
include(":data:tasks")
include(":features:audio")
include(":audio-player")
include(":data:audio")
