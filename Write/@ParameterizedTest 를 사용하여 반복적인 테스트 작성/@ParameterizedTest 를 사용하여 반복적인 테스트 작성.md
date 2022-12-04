기능을 테스트함에 있어 하나의 변수로 검증하는 것보다는 여러 변수로 검증하는 것이 테스트의 신뢰도가 높습니다.

보통의 테스트는 하나 함수가 한번만 실행됩니다. 때문에 여러 데이터에 대해 반복적인 테스트를 작성하는 것은 어려움이 따를 수 있습니다. 하지만 Junit5에서는 @ParameterizedTest를 제공함으로써 반복적인 테스트를 쉽게 구현할 수 있게 해 줍니다.

반복적인 테스트를 위한 어떤 데이터를 사용할 것인지 정해주어야 합니다.  
Junit5에서는 @ValueSource, @CsvSource, @MethodSource를 제공하여 테스트에 사용할 데이터 값 세트를 정의할 수 있습니다.

-   @ValueSource: String, Int, Long, Boolean 등 중에 하나의 타입을 선택하여 배열로 넘겨주면 순서대로 데이터로 사용한다.
-   @CsvSource: ,(콤마)로 구분된 문자열을 넣어주면 ,(콤마)를 기준으로 데이터를 사용한다.
-   @[MethodSource](): 데이터 값 세트를 생성하는 함수를 작성하면 함수 이름을 기준으로 데이터를 받아와 사용한다.  
    3가지의 방법들이 어떻게 사용되는지 같은 예시를 통해서 자세하게 알아보겠습니다.

---

### @ValueSource를 사용한 테스트

```
@ParameterizedTest(name = "ValueSource({0})")  
@ValueSource(longs = [1, 2])  
fun `valueSourceTest`(id: Long) {  
    val npc = Npc(id, "James", NpcType.NORMAL)  

    assertEquals(npc.id, id)  
    assertEquals(npc.name, "James")  
    assertEquals(npc.type, NpcType.NORMAL)  
}
```

ValueSource는 하나의 타입에 대해서만 값을 정의할 수 있습니다. 때문에 같은 유형의 변수가 2개 필요하다면 적합하지 않습니다.

만약 2가지 유형의 변수에 대해서 값을 정의하면 다음의 Error가 발생합니다.

```
Failed to initialize AnnotationConsumer: org.junit.jupiter.params.provider.ValueArgumentsProvider@273444fe
```

---

### @CsvSource를 사용한 테스트

```
@ParameterizedTest(name = "CsvSource({0}, {1}, {2})")  
@CsvSource("1, james, NORMAL", "2, Bob, QUEST")  
fun `csvSourceTest`(id: Long, name: String, type: NpcType) {  
    val npc = Npc(id, name, type)  

    assertEquals(npc.id, id)  
    assertEquals(npc.name, name)  
    assertEquals(npc.type, type)  
}
```

CsvSource는 csv라는 이름을 가진 것에 맞게 입력 문자열을 ,(콤마)로 구분하여 데이터로 사용합니다.  
문자열을 입력으로 넣어주어야 하기 때문에 오타가 발생할 수 있다는 위험이 있지만 다른 2가지의 데이터 값 세트 생성 방식에 비해 간편하게 사용할 수 있다는 장점이 있습니다.

---

### @MethodSource를 사용한 테스트

```
private fun getNpcList(): List<Npc> {  
    return listOf(  
        Npc(1, "james", NpcType.NORMAL),  
        Npc(2, "Bob", NpcType.QUEST)  
    )  
}
```

MethodSource 방식은 다른 2가지 방법과는 다르게 getNpcList 함수와 같이 데이터 값 세트를 생성하는 함수를 작성해야 합니다.

```
@ParameterizedTest(name = "MethodSource({0}, {1}, {2})")  
@MethodSource("getNpcList")  
fun `methodSourceTest`(dataNpc: Npc) {  
    val npc = Npc(dataNpc.id, dataNpc.name, dataNpc.type)  

    assertEquals(npc, dataNpc)  
}
```

만들어진 함수를 MethodSource의 인자로 넘겨주면 함수 안에 정의된 값을 사용할 수 있습니다.

MethodSource는 미리 함수를 정의해야 하지만 CsvSource처럼 오타에 대해 걱정할 필요가 없고, 복잡한 값이라도 손쉽게 사용할 수 있다는 장점이 있습니다.

### 결론

반복적인 테스트에서 데이터 값 세트를 생성하는 방법 3가지에 대해서 알아보았습니다.

-   @ValueSource는 하나의 타입 값만 필요한 경우에 사용할 수 있습니다. 하지만 보통 테스트에서 하나의 값만 사용하지 않기 때문에 사용에 어려움이 있을 것 같습니다.
-   @CsvSource는 문자열 배열에 저장된 순서대로 데이터 세트를 사용하고, 여러 데이터 타입을 사용할 수 있어 간단한 테스트에 적용할 것 같습니다.
-   @MethodSource는 미리 데이터 갑 세트 생성 함수를 작성해야 하는 번거로움이 있지만 복잡한 객체를 간편하게 사용할 수 있습니다. 또한 테스트 값 세트 생성 함수를 잘 분리하여 관리한다면 @ValueSource, @CsvSource 보다 유연하게 테스트 값을 변경할 수 있다는 장점을 가지고 있습니다.