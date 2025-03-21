allprojects {
    dependencies {
        // jooq (sql abstraction)
        implementation("org.jooq:jooq:3.20.2")
        implementation("org.jooq:jooq-kotlin:3.20.2")

        // db driver
        runtimeOnly("com.mysql:mysql-connector-j:9.2.0")

        // jsoup (html parser)
        implementation("org.jsoup:jsoup:1.19.1")

        // logger
        implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")
        implementation("org.slf4j:slf4j-simple:2.0.3")
        implementation("org.slf4j:slf4j-api:2.0.3")

        implementation(project(":generated"))
    }
}

subprojects {
    dependencies {
        implementation(project(":scripts"))
    }
}