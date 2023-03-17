package com.atzu68.learning.gia

import groovy.transform.TupleConstructor

@TupleConstructor
class ProjectVersion {
    int major
    int minor
    boolean release

    @Override
    String toString() {
        return "$major.$minor${release ? '' : '-SNAPSHOT'}"
    }
}