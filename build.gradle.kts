plugins {
    application
}

application {
    // Define the main class for the application.
    mainClass.set("com.marekscholle.sudoku.App")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation("junit:junit:4.13")
}
