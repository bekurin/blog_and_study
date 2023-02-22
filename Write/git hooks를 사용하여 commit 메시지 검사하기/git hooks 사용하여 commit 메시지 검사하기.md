commit 메시지를 구조화하고 빠르게 이해하기 위해 commit 컨밴션을 많이 사용하는데요.  
보통 컨벤션은 commit 메시지에 prefix를 작성하는 구조를 사용합니다.

가령

-   feat: 새로운 기능이 추가된 경우
-   docs: readme와 같은 문서를 수정한 경우
-   test: 테스트를 추가, 수정한 경우

와 같은 단어들을 prefix로 사용하는데요. 해당 단어들을 사용하여 commit 메시지를 작성하면 메시지를 읽는 것만으로 테스트를 수정했는지 새로운 기능을 추가했는지 이해할 수 있습니다.

commit 컨밴션은 문서로 정의해 두고 실제로 commit을 할 때 검사하지 않는 경우도 존재합니다.  
이런 경우 컨밴션을 사용하지 않은 commit이 repository에 반영될 수 있기 때문에 개발 중인 프로젝트에 컨밴션을 강제하고 싶은 니즈가 있을 수 있는데요.

이때, 사용할 수 있는 github 기능이 바로 hooks입니다.

먼저 `.git/hooks` 디렉터리 하위에 다음 내용을 포함한 `commit-msg` 파일을 생성합니다.

```
#!/bin/sh

commit_message=`cat $1`
if ! echo "$commit_message" | grep -E '^(feat|fix|docs|chore|test):' > /dev/null; then
    echo "Error: Commit message must start with 'feat:', 'fix:', 'docs:', 'chore:', 'test:'"
    exit 1
fi
```

위 코드는 commit 메시지가 feat, fix, docs, chore, test로 시작하지 않으면 Error를 반환하는 코드입니다.

이제 git commit 명령어 실행 시에 실행할 수 있도록 commit-msg 파일의 권한을 수정해 줍니다.

```
chmod +x commit-msg
```

이제 `commit-msg`가 정의한 대로 동작하는지 확인해 보겠습니다.

![[Screen Shot 2023-02-22 at 5.13.30 PM.png]]

update, Docs 같이 정의되지 않은 컨밴션을 사용하면 error가 발생합니다.

### 마무리

잘 정의된 commit 컨밴션을 사용하는 것은 협업하는데 도움을 주고, 의사소통 비용을 낮출 수 있어 효율적인 개발을 할 수 있습니다.

