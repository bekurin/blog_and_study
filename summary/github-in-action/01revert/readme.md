### revert 커밋

Q1: revert에 사용되는 commit log를 가져오는 방법이 (git log, git show)가 있다. 이 둘의 차이가 무엇인가?

```terminal
-- 이전 변경 지점으로 코드를 돌리는 것
git log
git revert <commit log>

-- revert로 인해 없어진 코드를 다시 원래 상태로 돌리는 것
git show
git revert <commit log>
```
