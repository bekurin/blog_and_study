### reset 명령어를 통해 커밋을 삭제할 수 있다.


가장 최근 커밋 1개 삭제
```
git reset HEAD^
git push origin +<branch name>
```

n개 커밋 삭제
```
git reser HEAD~n
git push origin +<branch name>
```

