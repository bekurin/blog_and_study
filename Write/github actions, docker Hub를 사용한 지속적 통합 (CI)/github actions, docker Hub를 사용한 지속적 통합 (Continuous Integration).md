Github Actions(이하 Actions)는 Job을 정의하여 자동화된 작업을 수행할 수 있게 해준다. Actions를 대중적으로 사용하는 법 중에는 CI/CD가 포함된다. 그중에서도 CI를 Actions를 사용하여 자동화하는 법에 대해 알아보겠다.

여러 언어와 프레임워크를 사용할 수 있지만 그 중에서도 Spring Boot + Kotlin를 선택하여 설명하겠다.

도커 image를 사용한 자동화 CI(지속적인 통합)는 다음의 과정으로 진행된다.

1.  코드를 수정하여 push 또는 pull request 이벤트를 발생한다.
2.  gradle 빌드를 한다.
3.  2의 결과로 나온 .jar와 사전에 정의된 Dockerfile을 기준으로 도커 image를 만든다.
4.  3의 결과를 Docker hub 또는 AWS ECR 등 공용 저장 공간에 저장한다.

Spring Boot 어플리케이션은 항상 200 OK를 반환하는 /api/liveness, /api/ready가 정의되어 있다.

```
@RestController  
@RequestMapping("/api")  
class HealthCheckController {  
    @GetMapping("/liveness")  
    fun livenessCheck(): HttpStatus {  
        return HttpStatus.OK  
    }  

    @GetMapping("/ready")  
    fun readinessCheck(): HttpStatus {  
        return HttpStatus.OK  
    }  
}
```

2가지 API는 요청이 오면 무조건 HttpStatus.OK를 반환한다. 추후 CD(지속적 배포)를 위한 kubernetes 구성 시에 활성 프로브와 준비성 프로브의 healthCheck를 실행하기 위해 만들었다.

아래 코드는 도커 image를 만들기 위한 Dockerfile 코드이다.

```
FROM khipu/openjdk17-alpine
EXPOSE 8080
ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} app.jar
RUN ["java","-jar","/app.jar"]
```

Dockerfile은 절차지향적으로 명령이 실행된다. 명령의 동작을 순서대로 기술하면 다음과 같다.

1.  도커 hub의 khipu/openjdk17-alpine 이미지를 사용하여 jdk 17 설정을 한다.
2.  8080 포트를 열어준다.
3.  build/libs 안에 있는 \*.jar 파일의 경로를 JAR\_FILE에 저장한다.
4.  3의 결과에 저장된 경로에 있는 파일을 현재 디렉터리에 app.jar 이름으로 저장한다.
5.  java -jar /app 명령어를 실행하여 저장된 app.jar 파일을 실행한다.

CI를 위한 Dockerfile까지 작성이 되었다. 이제 Actions를 사용하여 도커 image를 빌드하고, 도커 hub에 올리는 과정까지 자동화해보겠다.

```
name: Automate Publishing Docker Image To Docker Hub  

on:  
  push:  
    branches: [ "main" ]  
  pull_request:  
    branches: [ "main" ]
    
env:
  DOCKER_HUB_REPO: "ci-with-github-actions"
```

Job을 실행하기 전에 기본적인 정보들을 설정하는 코드이다. 각 예약어에 대한 설명은 다음과 같다.

-   name: Github에서 실행 Actions를 실행 시에 표시되는 이름이다.
-   push: branches에 입력된 branch에 push 이벤트가 발생하면 Actions를 실행한다.
-   pull\_request: branches에 입력된 branch에 pull\_request 이벤트가 발생하면 Actions를 실행한다.
-   env: Job 안에서 사용할 수 있는 변수를 설정하는 것으로 코드에서는 도커 hub의 repo 이름을 저장했다.

```
jobs:  
  build-and-push-docker-image:  
    runs-on: ubuntu-latest  
    steps:  
      - name: Check out the repo  
        uses: actions/checkout@v3
```

ubuntu-latest 버전을 사용하는 build-and-push-docker-image job을 생성하였다. 해당 job이 생성된 이후 가장 처음으로 하는 동작은 현재 repository의 root 디렉터리로 이동하는 것이다. 이때 [actions/checkout](https://github.com/actions/checkout)은 사전에 정의된 workflow이다.

```
      - name: Set up JDK 17
        uses: actions/setup-java@v1
        with:
          java-version: '17'
          cache: 'gradle'

      - name: Grant execute permission for gradlew  
        run: chmod +x gradlew  

      - name: Build with gradle  
        run: ./gradlew build
```

다음은 도커 image를 만들기 위한 gradle build 과정이다. 위 Step은 다음의 명령을 순서대로 실행한다.

1.  JDK 17을 설치한다. 이때 with를 통해 넘기 변수들을 사용하여 java version을 17로 설정하고, JDK 17 정보를 caching한다.
2.  gradle build를 위해 gradlew 권한을 높여준다.
3.  gradlew build 명령어를 실행하여 gradle build를 실행한다.

gradle build 과정에서 with, run, uses와 같은 키워드가 등장한다. 역할은 다음과 같다.

-   with: with 단 밑에 설정된 값은 사전에 정의된 workflow의 inputs에 저장된다.
-   run: 터미널 명령어를 실행할 때 사용하는 것으로 복잡하지 않은 명령에 대해 간편하게 사용할 수 있다.
-   uses: 복잡한 workflow를 정의한 Actions를 사용하는 것으로 workflow 정의에 따라 변수를 입력할 수 있다.

run과 uses는 명령을 실행한다는 관점에서 역할이 동일하다. 그러므로 Actions workflow를 작성할 때에 하나의 step에 run과 uses가 동시에 존재할 수 없다.

```
      - name: Log in to Docker Hub  
        uses: docker/login-action@v2  
        with:  
          username: ${{secrets.DOCKER_HUB_USERNAME}} 
          password: ${{secrets.DOCKER_HUB_PASSWORD}}
```

settings/secrets/actions에 저장된 변수들을 사용하여 도커 hub 로그인을 위한 workflow이다.

```
      - name: Set up Docker Buildx  
        id: buildx  
        uses: docker/setup-buildx-action@v2
```

buildx는 일반적은 build가 아닌 멀티플랫폼 build를 위해 사용된다. 따라서 멀티플랫폼 build가 필요하다면 해당 과정을 추가하고, 필요하지 않다면 추가하지 않아도 된다.

```
      - name: Build and push Docker image  
        uses: docker/build-push-action@v3  
        with:  
          context: .  
          file: ./Dockerfile  
          platforms: linux/amd64,linux/arm64  
          push: true  
          tags: ${{secrets.DOCKER_HUB_USERNAME}}/${{env.DOCKER_HUB_REPO}}:main
```

앞의 workflow는 Build and push Docker image 작업을 위한 사전 작업이라고 봐도 무방하다.

위 단계는 [docker/build-push-action](https://github.com/docker/build-push-action)를 사용하여 workflow를 실행하는 것으로 도커 image를 만든 후에 도커 hub에 업로드하는 것까지 실행한다. 해당 workflow를 위해 필요한 변수들의 내용은 다음과 같다.

-   context: work 디렉터리의 위치를 설정한다.
-   file: Dockerfile의 위치를 설정한다.
-   platforms: 사용하고자 하는 멀티 플랫품을 설정한다.
-   push: 도커 hub에 도커 image를 올릴지를 결정한다.
-   tags: 도커 hub의 repo 경로와 tagname을 설정한다.

여기까지 실행이 되었다면 도커 Hub에 정상적으로 image가 저장되었을 것을 기대할 수 있다.

![[docker-hub-upload.png]]

도커 hub에 접속하여 자신의 repo에 접속하면 도커 image가 저장된 것을 확인할 수 있다.

```
docker run -d -p 8080:8080 piaochung/ci-with-github-actions:main
```

도커 image가 잘 동작하는지 확인하기 위해 docker run을 통해 도커 Hub에 있는 이미지를 실행시켜 API 2개를 확인해보겠다.

![[api-liveness.png]]
![[api-ready.png]]

2가지 API가 모두 잘 동작하는 것을 확인할 수 있다.

### 마무리

Actions와 도커를 사용한 CI 작업을 진행해보았다. 이전에 사용했던 젠킨스와 비교하여 Actions의 장점을 나열하면 다음과 같다.

1.  Actions를 사용하여 복잡한 사전 작업을 쉽게 정의할 수 있다.
2.  이미 많은 기업에서 사용하는 CI 솔루션인 도커를 사용하여 실행과 관리가 편한다.
3.  도커 image를 통해 추후 실제 서비스를 한다면 쿠버네티스를 사용하여 분산 서비스를 만들기에 용이하다.

CI/CD는 이벤트를 인지, 데이터베이스 연결, 도커 image 만들기, 도커 image 업로드하기 등 여러 작업들을 수행해야 한다. 그러한 점에서 보았을 때 복잡한 작업들을 사전에 정의해놓고 간편하게 사용할 수 있다는 Actions의 장점은 여러 기업에서 많이 사용하는 가장 큰 이유일 것 같다.