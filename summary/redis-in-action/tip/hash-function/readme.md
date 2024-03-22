## 레디스 hash 함수 분석

레디스는 key-value store로 hash table로 값이 관리가 됩니다. hash table에서 가장 중요한 부분 중 하나는 바로 hash 값을 만드는 hash function의 구현일텐데요. redis에서는 어떻게 hash value를 생성하는지 알아보겠습니다.

다음은 dict.c에 구현된 dictGenHashFunction 함수의 구현입니다.
```c
static unsigned int dictGenHashFunction(const unsigned char *buf, int len) {
    unsigned int hash = 5381;

    while (len--)
        hash = ((hash << 5) + hash) + (*buf++); /* hash * 33 + c */
    return hash;
}
```

초기 hash 값 5381에 buf의 각 글자를 순환하면서 hash 33배 더하기 현자 문자의 아스키 코드를 더합니다.

예를 들어 buf가 "mysql"이고, len이 5인 경우 계산을 해보면 아래와 같습니다.
- 첫번째 문자 "m":
  - ASCII 값은 109이다.
  - 초기 hash 값은 5381이므로 (5381 * 33) + 109가 다음 hash 값이 됩니다.
- 두번째 문자 "y":
  - ASCII 값은 121이다.
  - 이전 단계에서 계산된 hash(177682) 값을 사용하여 새로운 hash 값을 생성합니다.
- 세번재, 네번째, 다섯번째까지 반복합니다.

이렇게 5번의 hash 값 계싼이 끝나면 210721292571이라는 값이 만들어지게 되고 이는 mysql이라는 key가 사용할 hash 값이 됩니다.
