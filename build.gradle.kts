plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "9.0.0-rc2"
}

group = "dev.astatic.nodestyclient"
version = "1.0.2"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")

    implementation("com.squareup.retrofit2:adapter-rxjava3:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.1.11")

    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("com.google.code.gson:gson:2.13.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar{
    isZip64 = true
    mergeServiceFiles()
}



publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = project.group as String
            artifactId = "nodesty-java-api-client"
            version = project.version as String

            from(components["java"])
        }
    }
}
