package com.atzu68.learning.gia

import org.gradle.api.Project
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult

class NotificationTestListener implements TestListener {

    private final Project project

    NotificationTestListener(Project project) {
        this.project = project
    }

    @Override
    void beforeSuite(TestDescriptor suite) {

    }

    @Override
    void afterSuite(TestDescriptor suite, TestResult result) {
        if (!suite.parent && result.getTestCount()) {
            def elapsedTestTime = result.endTime - result.startTime
            println "Elapsed time for execution of ${result.testCount} test(s): $elapsedTestTime ms"
        }
    }

    @Override
    void beforeTest(TestDescriptor testDescriptor) {

    }

    @Override
    void afterTest(TestDescriptor testDescriptor, TestResult result) {

    }
}
