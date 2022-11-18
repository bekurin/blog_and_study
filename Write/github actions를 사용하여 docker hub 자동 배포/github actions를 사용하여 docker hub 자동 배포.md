Github actions는 정의된 단계에 따라 자동화된 작업을 수행할 수 있도록 해준다. 
github actions를 대중적으로 사용하는 법 중에는 CI/CD가 포함된다. 그 중에서도 CI 부분을 github actions를 사용하여 자동화하는 법에 대해 알아보겠다.

여러 언어와 프레임워크를 사용할 수 있지만 그 중에서도 Spring Boot + Kotlin를 선택하여 설명하겠다.

CI(지속적인 통합)를 도커 image를 사용하여 자동화하기 위해서는 다음의 과정을 단계별로 진행해야한다.
1. 코드를 수정하여 push 또는 pull request 이벤트를 발생시킨다.
2. gradle 빌드를 한다.
3. 2의 결과로 나온 .jar와 사전에 정의된 Dockerfile을 기준으로 도커 image를 만든다.
4. 3의 결과를 Docker hub 또는 AWS ECR 등 공용 저장 공간에 저장한다.

Spring Boot 어플리케이션은 항상 200 OK를 반환하는 /api/liveness, /api/ready가 정의되어 있다.

```kotlin
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

2가지 API는 요청이 오면 무조건 HttpStatus.OK를 반환한다. 추후 CD를 위한 kubernetes 구성 시에 활성 프로브와 준비성 프로브의 healthCheck를 실행 하기 위해 만들었다.

아래 코드는 Dockerfile의 코드이다.
```Docker
FROM khipu/openjdk17-alpine
EXPOSE 8080
ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} app.jar
RUN ["java","-jar","/app.jar"]
```
Dockerfile은 작성 순서대로 명령이 실행된다. 명령의 동작을 순서대로 기술하면 다음과 같다.
1. khipu/openjdk17-alpine 이미지를 사용하여 jdk 17 설정을 한다.
2. 8080 포트를 열어준다.
3. build/libs 안에 있는 *.jar 경로를 JAR_FILE에  저장한다.
4. 3의 결과에 저장된 경로에 있는 파일을 현재 디렉토리에 app.jar 이름으로 저장한다.
5. java -jar /app 명령어를 실행하여 저장된 app.jar 파일을 실행한다.

CI를 위한 Dockfile까지 작성이 되었다. 이제 Github Actions를 사용하여 도커 image를 빌드하고, 도커 Hub에 올리는 과정까지 자동화 해보겠다.

```yaml
name: Automate Publishing Docker Image To Docker Hub  
  
on:  
  push:  
    branches: [ "main" ]  
  pull_request:  
    branches: [ "main" ]
```

Github Actions의 이름은 Automate Publishing Docker Image To Docker Hub이고, main 브렌치에 push 또는 pull request 이벤트가 발생할 때마다 Github Actions가 실행된다.

```YAML
jobs:  
  build-and-push-docker-image:  
    runs-on: ubuntu-latest  
    steps:  
      - name: Check out the repo  
        uses: actions/checkout@v3 
```

```YAML
      - name: Grant execute permission for gradlew  
        run: chmod +x gradlew  
          
      - name: Build with gradle  
        run: ./gradlew build
        with: 
	        cache: gradle
```

```YAML
      - name: Log in to Docker Hub  
        uses: docker/login-action@v2  
        with:  
          username: ${{secrets.DOCKER_HUB_USERNAME}} 
          password: ${{secrets.DOCKER_HUB_PASSWORD}}  
```

```YAML
      - name: Set up Docker Buildx  
        id: buildx  
        uses: docker/setup-buildx-action@v2  
```

```YAML
      - name: Build and push Docker image  
        uses: docker/build-push-action@v3  
        with:  
          context: .  
          file: ./Dockerfile  
          platforms: linux/amd64,linux/arm64  
          push: true  
          tags: ${{secrets.DOCKER_HUB_USERNAME}}/${{env.DOCKER_HUB_REPO}}:main
```

![[docker-hub-upload.png]]

```terminal
docker run -d -p 8080:8080 piaochung/ci-with-github-actions:main
```

![[api-ready.png]]


![[api-liveness.png]]


