# cherry pick 명령어
별개의 브랜치에서 특정 커밋만 추가하고 싶은 경우 사용할 수 있다.

### feature/origin, feature/target 브랜치가 있다고 가정

target의 커밋(hash: 123)을 origin에 추가하고 싶은 경우
```
# feature/origin 브랜치에서 아래 명령어 실행
git cherry-pick 123
```
