package com.atzu68.learning.gia.plugins.cloudbees.task.app


import com.atzu68.learning.gia.plugins.cloudbees.tasks.app.CloudBeesAppInfo
import org.gradle.api.Project
import org.gradle.api.UnknownTaskException
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class CloudBeesAppInfoSpec extends Specification {
    static final TASK_NAME = 'cloudBeesAppInfo'
    Project project

    def setup() {
        project = ProjectBuilder.builder().build()
    }

    def "App info task doesn't exist initially"() {
        when:
        project.tasks.named(TASK_NAME)
        then:
        thrown UnknownTaskException
    }

    def 'Adds app info task'() {
        when:
        project.tasks.register(TASK_NAME, CloudBeesAppInfo) {
            appId = 'gradle-in-action/todo'
            apiKey = 'myKey'
            secret = 'mySecret'
        }
        then:
        def task = project.tasks.named(TASK_NAME).get()
        task != null
        task.description == 'Returns the basic information about an application.'
        task.group == 'CloudBees'
        task.apiFormat == 'xml'
        task.apiVersion == '1.0'
        task.apiUrl == 'https://api.cloudbees.com/api'
        task.appId == 'gradle-in-action/todo'
        task.apiKey == 'myKey'
        task.secret == 'mySecret'
    }

    def "Executes app info task with wrong credentials"() {
        expect:
        def gradleProperties = getGradleProperties()
        gradleProperties['cloudbeesApiKey'] == null
        gradleProperties['cloudbeesApiSecret'] == null

        when:
        def task = project.tasks.register(TASK_NAME, CloudBeesAppInfo) {
            appId = 'gradle-in-action/todo'
            apiKey = gradleProperties['cloudbeesApiKey']
            secret = gradleProperties['cloudbeesApiSecret']
        }.get()

        task.start()

        then:
        thrown NullPointerException
        project.tasks.named(TASK_NAME).get() != null
    }

    private Properties getGradleProperties() {
        def props = new Properties()
        def gradlePropertiesFile = new File(System.getProperty('user.home'), '.gradle/gradle.properties')

        if (gradlePropertiesFile.exists()) {
            gradlePropertiesFile.withInputStream { props.load(it) }
        }

        addPropertyFromEnvVariable(props, 'cloudbeesApiKey')
        addPropertyFromEnvVariable(props, 'cloudbeesApiSecret')

        return props
    }

    private void addPropertyFromEnvVariable(Properties props, String key) {
        if (!props.containsKey(key)) {
            def env = System.getenv()
            String gradleEnvPropKey = "ORG_GRADLE_PROJECT_$key"

            if (env.containsKey(gradleEnvPropKey)) {
                props[key] = env[gradleEnvPropKey]
            }
        }
    }
}