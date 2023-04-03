package com.atzu68.learning.gia.plugins.cloudbees.tasks.app

import com.atzu68.learning.gia.plugins.cloudbees.tasks.CloudBeesTask
import com.cloudbees.api.BeesClient
import org.gradle.api.tasks.Input

class CloudBeesAppInfo extends CloudBeesTask {
    @Input
    String appId

    CloudBeesAppInfo() {
        super('Returns the basic information about an application.')
    }

    @Override
    void executeAction(BeesClient client) {
        def info = client.applicationInfo(appId)
    }

    @Override
    void onException() {
        logger.quiet "Application id : --Mocked-Application-ID--"
        logger.quiet "         title : --Mocked-Application-Title--"
        logger.quiet "       created : --Mocked-Application-Created--"
        logger.quiet "          urls : --Mocked-Application-URLs--"
        logger.quiet "        status : --Mocked-Application-Status--"
    }
}