### rebase 사용 예시
특정(main, develop 등등) branch의 작업을 현재 branch(feature/**)에 포함하고 싶은 경우 사용한다.

main branch의 작업을 feature branch의 작업에 포함하는 것을 가정한다.
```
git checkout main
git pull origin main
git checkout feature
git rebase main
```

고민해볼만한 작업
- 이미 push 된 작업(feature branch)에 대해서는 어떻게 동작하는지
