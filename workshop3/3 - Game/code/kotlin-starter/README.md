# LEESAH Quiz Starter-app

**System overview**
```bash
┌──────────────┐       ┌──────────────┐
│              │       │              │
│              │       │              │
│  Quizboard   │       │  Quizmaster  │
│              │       │              │
│              │       │              │
└──────────────┘       └──────────────┘
│  ▲                   │  ▲
│  │                   │  │
│  │                   │  │
▼  │                   ▼  │
┌──────────────────────────────────────────────────────────────┐
│                      Kafka                                   │
└──────────────────────────────────────────────────────────────┘
▲    │                           ▲    │
│    │                           │    │
│    ▼                           │    ▼
┌──────────────┐                 ┌──────────────┐
│              │                 │              │
│              │                 │              │
│    Team 1    │      .  .  .    │    Team n    │
│              │                 │              │
│              │                 │              │
└──────────────┘                 └──────────────┘
```

## Prerequisites
- Java 17 (or newer)
- IDEA
    - Plugins: [Kotlin](https://plugins.jetbrains.com/plugin/6954-kotlin) and [Co-Author](https://plugins.jetbrains.com/plugin/10952-co-author)
- Gradle 7.x.x

For Java and Gradle [SDKman](https://sdkman.io/) is recommended for installing.

## Setup 📝
1. Click the _Use this template_ button located on: https://github.com/navikt/leesah-game-template
2. Create a new public repository from the template with your team name with the navikt organisation as owner.
3. Clone your repository to your local machine
    - `git clone https://github.com/navikt/<YOUR_REPOSITORY_NAME>-leesah-quiz.git` (Remember `-leesah-quiz` after your repository name)

### settings.gradle.kts
Change root name in `settings.gradle.kts` - file to `<YOUR_REPOSITORY_NAME>-leesah-quiz`


### Nais & deploy
You will have to deploy your app to answer questions and play the game, and therefore you will need a `nais.yaml` file in root.

```yaml
apiVersion: nais.io/v1alpha1
kind: Application
metadata:
  name: <YOUR_TEAM_NAME> # CHANGE THIS! This will be the name of your application
  namespace: leesah-quiz
  labels:
    team: leesah-quiz
spec:
  image: {{image}}
  replicas:
    max: 1
    min: 1
  kafka:
    pool: nav-dev
  env:
    - name: QUIZ_TOPIC
      value: "leesah-quiz.leesah-rapid-v2"
```
(Remember to change the name in line 4 to your team name. Lowercase letters and do not add `-leesah-quiz`)

as well as a GitHub workflow file (lives in `.github/workflows`.
There is always an update guide for setting up the workflow for deploying to nais at [docs.nais.io](https://docs.nais.io/deployment/#deploy-with-github-actions).

If you would like to manually trigger a workflow you can add `workflow_dispatch` to the `on` array.

```yaml
on: [push, workflow_dispatch]
```

### Team API Secret
* Go to [NAIS Console](https://console.nav.cloud.nais.io/team/leesah-quiz/settings)
* Under `Deploy key` you'll find the `NAIS_DEPLOY_APIKEY` for leesah-quiz
* Copy key and add as a repository secret to your repository:
    * https://github.com/navikt/<YOUR_REPOSITORY_NAME>-leesah-quiz/settings/secrets/actions
    * Name: NAIS_DEPLOY_APIKEY

### Observability
Use the following command to observe the running status of your app

### Logs in Kibana
Go to [logs.adeo.no](https://logs.adeo.no/app/discover#/?_g=(filters:!(),refreshInterval:(pause:!t,value:60000),time:(from:now-90d%2Fd,to:now))&_a=(columns:!(level,message,envclass,application,pod),filters:!(),index:'96e648c0-980a-11e9-830a-e17bbd64b4db',interval:auto,query:(language:kuery,query:'application:%20%22<YOUR_TEAM_NAME>%22%20and%20%22QUESTION%22'),sort:!(!('@timestamp',desc)))) to see your application logs in Kibana.
When "inside" Kibana you need to change `<YOUR TEAM NAME>` to your team name.

### Useful kubectl-commands
* See name and status of pods of your app:
    * `kubectl get pod -n leesah-quiz -l app=<APP_NAME>`
    * if you wish to continuously trace the status of pods of your app, you can add the flag `-w`
* View logs of pods of your app:
    * Too see logs for all your pods: `kubectl logs -n leesah-quiz -l app=<APP_NAME>` 
    * To see logs for one specifig pod: `kubectl logs -n leesah-quiz <POD_NAME>`
    * You can find name(s) of your pod(s) with the previous command
    * If you wish to continuously trace logs, you can add the flag `-f`

## Developing your quiz participant 🤖
Your challenge is to implement a QuizParticipant that answers all the question messages that are published by the quizmaster 🧙.
You are free to develop your application any way you want, but this starter project comes with som useful boilerplate to help you quickly get to the fun part; answering questions! 🎉

The code you need to modify is all located in `src/main/kotlin/no/nav/QuizApplication.kt`.

From the command-line in the project root run:

**To build the app locally**
```bash
 ./gradlew build
```

**To run the app locally**
```bash
java -jar build/libs/app.jar
```

### First task
Choose a hex-color (6 characters, without #) in `handleRegisterTeam()` and deploy the application to NAIS!

Good luck! Remember to ask questions! ❤️
