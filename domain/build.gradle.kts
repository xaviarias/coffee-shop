plugins {
    id("com.coffeeshop.library-conventions")
    `java-test-fixtures`
}

dependencies {
    api("org.javamoney:moneta:1.4.2")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    compileOnly("org.projectlombok:lombok:1.18.28")
}
