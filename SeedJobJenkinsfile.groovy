node {
    try {
        stage("Build SeedJob by projects") {
            sh """
                ls
            """
            projectWithJobDsl().each { app -> createProjectJobs(app) }
        }

        stage("Build ListView") {
            git branch: "master", url: "https://github.com/Dkra/jenkins-job-dsl-example"
            sh """
                ls
            """
            jobDsl ignoreMissingFiles: true, targets: "./listView/*.groovy"
        }
    } catch (err) {
        throw err
    } finally {
        sh """
            ls
        """

    }
}


def createProjectJobs(app) {
    stage("Build [${app.name}] Seed Job") {
        sh """
            echo "------Start Building [${app.name}]---------------------"
        """
        git branch: "${app.branch}", url: "${app.repo_url}"
        jobDsl ignoreMissingFiles: true, targets: "./jenkins/**/seed.groovy"
        sh """
            ls -al
            echo "---------------------------"
        """
    }
}

def projectWithJobDsl() {
  return [
    [
      name: 'cra-jenkins-1',
      repo_url: 'https://github.com/Dkra/cra-jenkins',
      branch: 'jenkins'
    ],
    [
      name: 'cra-jenkins-2',
      repo_url: 'https://github.com/Dkra/cra-jenkins',
      branch: 'jenkins'
    ]
  ]
}
