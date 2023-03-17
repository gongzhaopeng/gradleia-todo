package com.atzu68.learning.gia

import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.execution.TaskExecutionGraphListener

class ReleaseVersionListener implements TaskExecutionGraphListener {
    private final static releaseTaskPath = ':release'

    void graphPopulated(TaskExecutionGraph taskGraph) {
        if (taskGraph.hasTask(releaseTaskPath)) {
            def project = taskGraph.allTasks.find { it.path == releaseTaskPath }.project

            if (!project.version.release) {
                project.version.release = true
                project.ant.propertyfile(file: project.versionFile) {
                    entry(key: 'release', type: 'string', operation: '=', value: 'true')
                }
            }
        }
    }
}
