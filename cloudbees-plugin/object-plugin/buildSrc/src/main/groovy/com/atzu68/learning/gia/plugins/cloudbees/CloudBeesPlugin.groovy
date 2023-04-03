package com.atzu68.learning.gia.plugins.cloudbees

import com.atzu68.learning.gia.plugins.cloudbees.tasks.CloudBeesTask
import com.atzu68.learning.gia.plugins.cloudbees.tasks.app.CloudBeesAppDeployWar
import com.atzu68.learning.gia.plugins.cloudbees.tasks.app.CloudBeesAppInfo
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.WarPlugin

class CloudBeesPlugin implements Plugin<Project> {
    static final String EXTENSION_NAME = 'cloudBees'

    @Override
    void apply(Project project) {
        project.plugins.apply(WarPlugin)
        project.extensions.create(EXTENSION_NAME, CloudBeesPluginExtension)
        addTasks(project)
    }

    static private void addTasks(Project project) {
        project.tasks.withType(CloudBeesTask).configureEach {
            def extension = project.extensions.findByName(EXTENSION_NAME)
            apiUrl = extension.apiUrl
            apiKey = extension.apiKey
            secret = extension.secret
        }

        addAppTasks(project)
    }

    static private void addAppTasks(Project project) {
        project.tasks.register('cloudBeesAppInfo', CloudBeesAppInfo) {
            appId = getAppId(project)
        }

        project.tasks.register('cloudBeesAppDeployWar', CloudBeesAppDeployWar) {
            appId = getAppId(project)
            message = project.hasProperty('message') ? project.message : null
            warFile = getWarFile(project)
        }
    }

    static private String getAppId(Project project) {
        project.hasProperty('appId') ? project.appId : project.extensions.findByName(EXTENSION_NAME).appId
    }

    static File getWarFile(Project project) {
        project.hasProperty('warFile') ? new File(project.warFile) : project.tasks.named(WarPlugin.WAR_TASK_NAME).get().archivePath
    }
}
