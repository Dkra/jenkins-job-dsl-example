import groovy.json.JsonSlurperClassic

node {
    try {
        // For workspace of pipeline
        stage("Clean legacy data") {
          // sh """
          //   rm -rf .git
          //   rm -rf *.*
          //   rm -rf *
          // """
        }

        stage("Build SeedJob by projects") {
            sh """
                ls -al
            """
            def json = readFile(file: "./project/projects.json")
            def jobArray = new JsonSlurperClassic().parseText(json)
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
            ls -al
        """
        deleteDir()

    }
}


def createProjectJobs(app) {
    stage("Build [${app.name}] Seed Job") {
        sh """
            echo "------Start Building [${app.name}]------"
        """
        git branch: "${app.branch}", url: "${app.repo_url}"
        jobDsl ignoreMissingFiles: true, targets: "./jenkins/**/seed.groovy"
        sh """
            ls -al
            echo "----------------------------------------"
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
