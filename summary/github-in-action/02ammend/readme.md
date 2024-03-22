### commit --amend

--amend 명령어는 직전 커밋의 메시지를 변경할 수 있게 해줍니다.
명령어를 사용할 수 있는 경우는 push 하기 전후로 나눌 수 있다.

push가 된 경우
```
git commit --amend
git push -f <your branch>
<!-- -f 명령어 사용은 굉장히 조심해야한다고 한다.  -->
```

push가 되지 않은 경우
```
git commit --amend
<!-- 커밋 메시지 변경 후 필요할 때 push -->
```
