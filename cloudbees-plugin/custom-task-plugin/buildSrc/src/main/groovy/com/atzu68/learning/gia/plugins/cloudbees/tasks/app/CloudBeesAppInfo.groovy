package com.atzu68.learning.gia.plugins.cloudbees.tasks.app

import com.cloudbees.api.ApplicationInfo
import com.cloudbees.api.BeesClient
import org.gradle.api.*
import org.gradle.api.tasks.*

class CloudBeesAppInfo extends DefaultTask {
    @Input
    String apiUrl
    @Input
    String apiKey
    @Input
    String secret
    @Input
    String apiFormat
    @Input
    String apiVersion
    @Input
    String appId

    CloudBeesAppInfo() {
        description = 'Returns the basic information about an application.'
        group = 'CloudBees'
    }

    @TaskAction
    void start() {
        def client = new BeesClient(apiUrl, apiKey, secret, apiFormat, apiVersion)

        ApplicationInfo info

        try {
            info = client.applicationInfo(appId)
        } catch (Exception e) {
            logger.quiet 'Mocking acquiring CloudBees application information...'
            logger.quiet "Thrown exception message: ${e.message}"
        }

        logger.quiet "Application id : --Mocked-Application-ID--"
        logger.quiet "         title : --Mocked-Application-Title--"
        logger.quiet "       created : --Mocked-Application-Created--"
        logger.quiet "          urls : --Mocked-Application-URLs--"
        logger.quiet "        status : --Mocked-Application-Status--"
    }
}