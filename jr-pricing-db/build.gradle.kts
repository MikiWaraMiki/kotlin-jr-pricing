import com.rohanprabhu.gradle.plugins.kdjooq.*

plugins {
    id("org.flywaydb.flyway") version "8.0.4"
    id("com.rohanprabhu.kotlin-dsl-jooq") version "0.4.6"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")
    jooqGeneratorRuntime("org.postgresql:postgresql:42.2.24")
}

flyway {
    url = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/jr-pricing-local"
    user = System.getenv("DB_USER") ?: "root"
    password = System.getenv("DB_PASSWORD") ?: "root"
    baselineOnMigrate = true
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

jooqGenerator {
    configuration("primary", project.the<SourceSetContainer>()["main"]) {
        configuration = jooqCodegenConfiguration {
            jdbc {
                username = System.getenv("DB_USER") ?: "root"
                password = System.getenv("DB_PASSWORD") ?: "root"
                driver = "org.postgresql.Driver"
                url = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/jr-pricing-local"
            }

            generator {
                target {
                    packageName = "jrpricing.db.jooq.gen"
                    directory = "${project.buildDir}/generated/jooq/primary"
                }

                database {
                    name = "org.jooq.meta.postgres.PostgresDatabase"
                    inputSchema = "public"
                }
            }
        }
    }
}
