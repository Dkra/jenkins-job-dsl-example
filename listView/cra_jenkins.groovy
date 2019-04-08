listView('cra-jenkins-Project') {
    description('All cra-jenkins related Projects')
    filterBuildQueue()
    filterExecutors()
    jobs {
        name('release-projectA')
        regex(/cra-jenkins.+/)
    }
    jobFilters {
        status {
            status(Status.UNSTABLE)
        }
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}
