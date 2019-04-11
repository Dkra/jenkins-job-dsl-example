import groovy.json.JsonSlurperClassic

node {
    try {
        stage("Clone Root SeedJob Repository") {
            git branch: "master", url: "https://github.com/Dkra/jenkins-job-dsl-example"
        }

        stage("Build ListView") {
            jobDsl ignoreMissingFiles: true, targets: "./listView/*.groovy"
        }

        stage("Build SeedJob from [projects.json]") {
            def json = readFile(file: "./project/projects.json")
            def jobArray = new JsonSlurperClassic().parseText(json)
            jobArray.each { app -> createProjectJobs(app) }
        }

    } catch (err) {
        throw err
    } finally {
        deleteDir()
    }
}


def createProjectJobs(app) {
    stage("Build [${app.name}] Seed Job") {
        sh """
            echo "------Start Building [${app.name}]------"
        """
        git branch: "${app.branch}", url: "${app.repo_url}"
        jobDsl ignoreMissingFiles: true, targets: "./jenkins/**/seedJob.groovy"
        sh """
            ls -al
            echo "----------------------------------------"
        """
    }
}
