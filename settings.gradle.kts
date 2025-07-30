rootProject.name = "demo"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
    }
}
