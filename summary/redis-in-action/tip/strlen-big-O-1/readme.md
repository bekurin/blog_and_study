# redis의 strlen은 왜 O(1)일까?

redis 명령어 중에 strlen은 O(1)로 어떠한 환경에서도 동일한 속도를 보장합니다.
일반적으로 문자열의 길이를 알기 위해서는 O(n)의 시간복잡도를 가집니다.
(문자열의 끝 "\0"이 나오기 전까지 다음 문자열을 순환해야하기 때문입니다)

redis는 어떻게 O(1) 시간복잡도를 보장할까?
redis의 문자열 구조를 보면 해답을 알 수 있습니다.

다음은 redis 문자열 구조입니다.
```
/* Note: sdshdr5 is never used, we just access the flags byte directly.  
* However is here to document the layout of type 5 SDS strings. */  
struct __attribute__ ((__packed__)) sdshdr5 {  
	unsigned char flags; /* 3 lsb of type, and 5 msb of string length */  
	char buf[];  
};  

// sdshdr16, 32, 64도 같은 구조
struct __attribute__ ((__packed__)) sdshdr8 {  
	uint8_t len; /* used */  
	uint8_t alloc; /* excluding the header and null terminator */  
	unsigned char flags; /* 3 lsb of type, 5 unused bits */  
	char buf[];  
};  
...
```

sdshdr5/8/16/32/64가 존재하는데 사용하지 않는 sdshdr5를 제외하면 모두 같은 구조를 가집니다. 차이점은 각 필드의 할당되는 메모리 크기입니다.

필드를 살펴보면 알 수 있듯이 len,alloc, flags, buf가 있는데요. 각 필드는 다음과 같은 역할을 지닙니다.
- len은 문자열의 길이
- alloc,flags는 알아보겠습니다.
- buf는 문자열을 저장하는 변수

redis 명령어인 strlen 명령을 수행하면 아래 함수가 실행되게 되는데요.

```
static inline size_t sdslen(const sds s) {  
	unsigned char flags = s[-1];  
	switch(flags&SDS_TYPE_MASK) {  
		case SDS_TYPE_5:  
			return SDS_TYPE_5_LEN(flags);  
		case SDS_TYPE_8:  
			return SDS_HDR(8,s)->len;  
		case SDS_TYPE_16:  
			return SDS_HDR(16,s)->len;  
		case SDS_TYPE_32:  
			return SDS_HDR(32,s)->len;  
		case SDS_TYPE_64:  
			return SDS_HDR(64,s)->len;  
	}  
	return 0;  
}
```

함수의 구현으로 봐도 타입에 따라 문자열 구조체에 저장되는 len 값을 반환하는 것을 알 수 있습니다.

그렇다면 1가지 의문이 들 수 있는데요.
```
redis 문자열 구조체는 문자열의 크기에 따라 나눠지게 되는데. 값을 새롭게 할당하게 되면 기존의 구조체를 다른 구조체로 바꿔주는 것인지? 아니면 매번 새롭게 만드는 것인지?
```

위 질문에 대한 해답은 아래 코드를 통해 알 수 있습니다.

```
/* Create a new hisds string starting from a null terminated C string. */  
hisds hi_sdsnew(const char *init) {  
	size_t initlen = (init == NULL) ? 0 : strlen(init);  
	return hi_sdsnewlen(init, initlen);  
}  
  
/* Duplicate an hisds string. */  
hisds hi_sdsdup(const hisds s) {  
	return hi_sdsnewlen(s, hi_sdslen(s));  
}
```

hi_sdsnewlen은 문자열 구조체 타입을 결정하고, sdshdr 구조체를 생성합니다.
hi_sdsnew, hi_sdsdup 함수에서 할 수 있듯이 기존에 값이 있던 것을 update 하지 않고, 매번 새롭게 sdshdr 구조체를 그 길이에 따라 나눠주는 것을 알 수 있습니다.

부분 update라는 개념은 없고, 항상 덮어 쓰도록 구현되어 있습니다.
