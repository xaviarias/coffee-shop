rootProject.name = "coffee-shop"

include(":domain")
include(":infrastructure")
include(":application")

project(":domain").name = "${rootProject.name}-domain"
project(":infrastructure").name = "${rootProject.name}-infrastructure"
project(":application").name = "${rootProject.name}-application"
