

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

```yaml
name: Automate Publishing Docker Image To Docker Hub  
  
on:  
  push:  
    branches: [ "main" ]  
  pull_request:  
    branches: [ "main" ]
```

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
```

```YAML
      - name: Log in to Docker Hub  
        uses: docker/login-action@v2  
        with:  
          username: ${{ secrets.DOCKER_HUB_USERNAME }}  
          password: ${{ secrets.DOCKER_HUB_PASSWORD }}  
```

```YAML
      - name: Set up Docker Buildx  
        id: buildx  
        uses: docker/setup-buildx-action@v2  
```

```YAML
     - name: Extract metadata (tags, labels) for Docker  
        id: meta  
        uses: docker/metadata-action@98669ae865ea3cffbcbaa878cf57c20bbf1c6c38  
        with:  
          images: ${{ secrets.DOCKER_HUB_REPO }}  
```

```YAML
      - name: Build and push Docker image  
        uses: docker/build-push-action@v3  
        with:  
          context: .  
          file: ./Dockerfile  
          platforms: linux/amd64,linux/arm64  
          push: true  
          tags: ${{ steps.meta.outputs.tags }}  
          labels: ${{ steps.meta.outputs.labels }}
```

![[docker-hub-upload.png]]

```terminal
docker run -d -p 8080:8080 piaochung/ci-with-github-actions:main
```

![[api-ready.png]]


![[api-liveness.png]]


