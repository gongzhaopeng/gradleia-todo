package com.atzu68.learning.gia.plugins.cloudbees.tasks.app

import com.atzu68.learning.gia.plugins.cloudbees.tasks.CloudBeesTask
import com.cloudbees.api.BeesClient
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile

class CloudBeesAppDeployWar extends CloudBeesTask {
    @Input
    String appId
    @Input
    String message
    @InputFile
    File warFile

    CloudBeesAppDeployWar() {
        super('Deploys a new version of an application using a WAR file.')
    }

    @Override
    void executeAction(BeesClient client) {
        logger.quiet "Deploying WAR '${warFile.canonicalPath}' to application ID '$appId' with message '$message'"
        def response = client.applicationDeployWar(appId, null, message, warFile, null, null)
    }

    @Override
    void onException() {
        logger.quiet "Application uploaded successfully to: '--Mocked-Application-URL--'"
    }
}
