pluginManagement {
    repositories {
        google()
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

rootProject.name = "Yamba"
include(":app")
include(":core")
include(":core:domain")
include(":core:domain:domainuser")
include(":core:domain:domainaddress")
include(":core:domain:domainannouncement")
include(":core:database")
include(":core:model")
include(":core:network")
include(":core:notifications")
