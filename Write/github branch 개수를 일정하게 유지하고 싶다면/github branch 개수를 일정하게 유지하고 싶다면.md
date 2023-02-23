
cleanup_branches.sh 파일을 생성한다.
```bash
#!/bin/bash

KEEP_BRANCHES=10

BRANCH_LIST=$(git for-each-ref --sort=-committerdate refs/heads/ --format='%(refname:short)')

BRANCH_COUNT=$(echo "${BRANCH_LIST}" | wc -l)

if [ "${BRANCH_COUNT}" -gt "${KEEP_BRANCHES}" ]; then
  OLDEST_BRANCH=$(echo "${BRANCH_LIST}" | tail -n +$((${KEEP_BRANCHES} + 1)) | head -n 1)

  git push origin --delete "${OLDEST_BRANCH}"
  git branch -D "${OLDEST_BRANCH}"
fi
```

위 파일을 github actions 실행 단계에 추가해둔다. 또는 임의로 실행하는 것도 가능하다.