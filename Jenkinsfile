import groovy.json.JsonSlurperClassic

node {
    try {
        // For workspace of pipeline
        stage("Clean legacy data & Clone") {
          git branch: "master", url: "https://github.com/Dkra/jenkins-job-dsl-example"
          sh """
              ls -al
          """
          // sh """
          //   rm -rf .git
          //   rm -rf *.*
          //   rm -rf *
          // """
        }

        stage("Build ListView") {
            sh """
                ls
            """
            jobDsl ignoreMissingFiles: true, targets: "./listView/*.groovy"
        }

        stage("Build SeedJob by projects") {

            def json = readFile(file: "./project/projects.json")
            def jobArray = new JsonSlurperClassic().parseText(json)
            jobArray.each { app -> createProjectJobs(app) }
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
