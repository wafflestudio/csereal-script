allprojects {
    dependencies {
        implementation(project(":generated"))
    }
}

subprojects {
    dependencies {
        implementation(project(":scripts"))
    }
}