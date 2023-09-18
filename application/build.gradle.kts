plugins {
    id("com.coffeeshop.library-conventions")
}

dependencies {
    api(project(":coffee-shop-domain"))
    testImplementation(testFixtures(project(":coffee-shop-domain")))
}
