plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "csereal-script"
include(":generated")
include(":scripts")

// You should include your script subprojects here
include(":scripts:remove-html-wrapped")