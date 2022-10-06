package com.example.core.l_tip

class Post(
        val id: Long? = null,
        val title: String,
        val description: String,
        val author: String
)

data class PostTitleDto(
        val id: Long,
        val title: String
)

fun toPostTitleDtoArray(posts: Array<Post?>): Array<PostTitleDto> {
    return posts.map { post ->
        PostTitleDto(
                post?.id ?: throw Error("Post object or id field is null"),
                post.title
        )
    }.toTypedArray()
}

/**
 * 안전한 호출과 엘비스 연산자를 사용하면 null 값 확인하는 코드를 간결하고 짧게 작성할 수 있다.
 */
fun main() {
    val posts = listOf<Post?>(
            Post(1L, "코린이의 코고수 여정", "빈칸", "hangman"),
            Post(null, "이거 왜 id가 없어요?", "없음", "hangman"),
    )

    for (dto in toPostTitleDtoArray(posts.toTypedArray())) {
        println("dto = $dto")
    }
}