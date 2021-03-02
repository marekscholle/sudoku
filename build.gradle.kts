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
    testImplementation("junit:junit:4.13")
}
