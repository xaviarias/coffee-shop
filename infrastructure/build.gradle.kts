plugins {
    id("com.coffeeshop.application-conventions")
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    `jvm-test-suite`
}

dependencies {
    implementation(project(":coffee-shop-application"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    testImplementation(testFixtures(project(":coffee-shop-domain")))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

application {
    mainClass.set("com.coffeeshop.infra.CoffeeShopApplication")
}

testing {
    suites {
        register<JvmTestSuite>("integrationTest") {
            dependencies {
                implementation(project())
                implementation(project(":coffee-shop-domain"))
                implementation("org.springframework.boot:spring-boot-starter-test")
            }
            targets {
                all {
                    testTask.configure {
                        val test by getting(JvmTestSuite::class)
                        shouldRunAfter(test)
                    }
                }
            }
        }
        withType(JvmTestSuite::class).configureEach {
            useJUnitJupiter("5.9.3")
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("integrationTest"))
}
