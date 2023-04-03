package com.atzu68.learning.gia.plugins.cloudbees

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownTaskException
import org.gradle.api.plugins.WarPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class CloudBeesPluginSpec extends Specification {
    static final APP_INFO_TASK_NAME = 'cloudBeesAppInfo'
    static final APP_DEPLOY_WAR_TASK_NAME = 'cloudBeesAppDeployWar'
    Project project

    def setup() {
        project = ProjectBuilder.builder().build()
    }

    def "App info task doesn't exist initially"() {
        when:
        project.tasks.named(APP_INFO_TASK_NAME)
        then:
        thrown UnknownTaskException
    }

    def "App deploying task doesn't exist initially"() {
        when:
        project.tasks.named(APP_DEPLOY_WAR_TASK_NAME)
        then:
        thrown UnknownTaskException
    }

    def "Applies plugin and sets extension values"() {
        when:
        project.apply plugin: 'cloudbees'

        project.cloudBees {
            apiKey = 'myKey'
            secret = 'mySecret'
            appId = 'todo'
        }

        then:
        project.plugins.hasPlugin(WarPlugin)
        project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null

        Task appInfoTask = project.tasks.named(APP_INFO_TASK_NAME).get()
        appInfoTask != null
        appInfoTask.description == 'Returns the basic information about an application.'
        appInfoTask.group == 'CloudBees'
        appInfoTask.apiKey == 'myKey'
        appInfoTask.secret == 'mySecret'
        appInfoTask.appId == 'todo'
        Task appDeployWarTask = project.tasks.named(APP_DEPLOY_WAR_TASK_NAME).get()
        appDeployWarTask != null
        appDeployWarTask.description == 'Deploys a new version of an application using a WAR file.'
        appDeployWarTask.group == 'CloudBees'
        appDeployWarTask.apiKey == 'myKey'
        appDeployWarTask.secret == 'mySecret'
        appDeployWarTask.appId == 'todo'
    }
}
