package dependencies

object Dep {

    private const val kotlinVersion = "1.3.61"

    object GradlePlugin {
        const val appEngine = "com.google.cloud.tools:appengine-gradle-plugin:2.2.0"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val shadow = "com.github.jengelman.gradle.plugins:shadow:5.0.0"
    }

    object Jetty {
        private const val version = "9.4.29.v20200521"
        const val server = "org.eclipse.jetty:jetty-server:$version"
        const val webapp = "org.eclipse.jetty:jetty-webapp:$version"
        const val util = "org.eclipse.jetty:jetty-util:$version"
        const val annotations = "org.eclipse.jetty:jetty-annotations:$version"
    }

    object Servlet {
        const val api = "javax.servlet:javax.servlet-api:4.0.1"
    }

    object Lombok {
        const val core = "org.projectlombok:lombok:1.18.12"
        const val compiler = core
    }

    object Gson {
        const val core = "com.google.code.gson:gson:2.8.5"
    }

    object Guice {
        private const val version = "4.2.3"
        const val core = "com.google.inject:guice:$version"
        const val servlet = "com.google.inject.extensions:guice-servlet:$version"
    }

    object Database {
        const val mysql = "mysql:mysql-connector-java:8.0.16"
        const val hikariCp = "com.zaxxer:HikariCP:3.3.1"

        object Doma {
            private const val version = "2.24.0"
            const val core = "org.seasar.doma:doma:$version"
            const val compiler = "org.seasar.doma:doma:$version"
        }

    }

    object GCP {
        const val pubsub = "com.google.cloud:google-cloud-pubsub:1.108.0"
    }

}