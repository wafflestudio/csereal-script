plugins {
    kotlin("jvm") version "2.0.20"
}

allprojects {
    group = "com.wafflestudio"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    dependencies {
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }
    kotlin {
        jvmToolchain(21)
    }
}



