# git hooks 사용하기

## .git/hooks 디렉터리 하위에 commit-msg 파일을 생성한다.
```
vi commit-msg
```

## commit-msg 파일에 아래 코드를 입력한다.
```
#!/bin/sh

commit_message=`cat $1`
if ! echo "$commit_message" | grep -E '^(feat|fix|docs|chore|test):' > /dev/null; then
    echo "Error: Commit message must start with 'feat:', 'fix:', 'docs:', 'chore:', 'test:'"
    exit 1
fi
```

## commit-msg의 파일 권한을 수정한다.
```
chmod +x commit-msg
```

이제 feat, fix, docs, chore, test로 시작하지 않는 커밋 메시지는 커밋할 수 없다.
