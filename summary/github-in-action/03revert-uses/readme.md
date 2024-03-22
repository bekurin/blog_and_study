### revert 활용 예시
배포를 하기 위해 반드시 feature/** -> develop -> main 으로 merge 되어야하는 순서가 있다고 가정한다.
그렇다면 여러 작업을 하다보면 다른 사람의 작업과 develop->main으로 합쳐지기 위한 코드가 섞일 수 있다.

이때, revert를 통해 작업을 순서대로 PR하면 된다.
```
git revert <commit key>
git push origin <branch name>
<!-- A 작업 PR 생성 후 merge -->

git show
git revert <commit key>
git push origin <branch name>
<!-- B 작업 PR 생성 후 merge -->
```
