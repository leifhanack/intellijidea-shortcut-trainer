import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType

plugins {
    kotlin("jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.5.0"
}

group = "strug.intellijidea.shortcuttrainer"
version = "1.1.1"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create(IntelliJPlatformType.IntellijIdeaCommunity, "2023.1.5")
        bundledPlugin("com.intellij.java")
    }

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "231"

        }
        version = project.version.toString()
        changeNotes = """
            <ul>
                <li>Changed default shortcut to <strong>Cmd-Shift-P</strong> on macOS or <strong>Ctrl-Shift-P</strong> on Windows/Linux</li>
                <li>Set compatibility with all future IDEs by removing <code>untilBuild</code></li>
                <li>Updated to IntelliJ Platform Gradle Plugin v2</li>
                <li>Updated to JUnit 5</li>
            </ul>
        """.trimIndent()
    }

    signing {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishing {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}
