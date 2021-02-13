plugins {
    `java-library`
    kotlin("jvm") version "1.4.21"
    `maven-publish`
}

group = "ca.jahed"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("junit", "junit", "4.12")
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = "ca.jahed"
            artifactId = "rtpoet"

            from(components["java"])

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name.set("RTPoet")
                description.set("Library to compose UML-RT models")
                url.set("https://github.com/kjahed/rtpoet")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("kjahed")
                        name.set("Karim Jahed")
                        email.set("jahed@cs.queensu.ca")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/kjahed/rtpoet.git")
                    developerConnection.set("scm:git:ssh://github.com/kjahed/rtpoet.git")
                    url.set("https://github.com/kjahed/rtpoet")
                }
            }
        }
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
