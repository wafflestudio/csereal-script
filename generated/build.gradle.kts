plugins {
    id("org.jooq.jooq-codegen-gradle") version "3.20.2"
}

dependencies {
    // jooq (sql abstraction & code generation)
    implementation("org.jooq:jooq:3.20.2")
    implementation("org.jooq:jooq-kotlin:3.20.2")
    implementation("org.jooq:jooq-meta:3.20.2")
    implementation("org.jooq:jooq-codegen:3.20.2")
    implementation("org.jooq:jooq-codegen-gradle:3.20.2")
    implementation("com.mysql:mysql-connector-j:9.2.0")
    jooqCodegen("com.mysql:mysql-connector-j:9.2.0")
}
jooq {
    configuration {
        // url, username, password should be configured in environment variables
        jdbc {
            driver = "com.mysql.cj.jdbc.Driver"
            url = "jdbc:mysql://127.0.1:3306/csereal?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul"
            username = "root"
            password = "password"
        }
        generator {
            name = "org.jooq.codegen.KotlinGenerator"
            database {
                name = "org.jooq.meta.mysql.MySQLDatabase"
                inputSchema = "csereal"
                includes = ".*"
                excludes = "flyway_schema_history"
            }
            generate {
                isInterfaces = true
                isDaos = true
                isGeneratedAnnotation = true
                isKotlinNotNullPojoAttributes = true
                isKotlinNotNullRecordAttributes = true
                isKotlinNotNullInterfaceAttributes = true
                isPojosAsKotlinDataClasses = true
            }
            target {
                packageName = "com.wafflestudio.generated.jooq"
                directory = "src/main/kotlin"
            }
        }
    }
}
