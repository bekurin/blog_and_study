import { useState } from "react"
import { PageResponse } from "../../apis/PageResponse"
import { IPost, PostQueryParams } from "./types"
import { Paginate } from "../../pagination/types"
import fetchAllPostApi from "../../apis/post/fetchAllPostApi"
import Post from "./Post"
import { textSpanContainsPosition } from "typescript"

const PostContainer = () => {
    const [posts, setPosts] = useState<PageResponse<IPost> | null>(null)
    const [params, setQueryParams] = useState<PostQueryParams>({
        title: ""
    })
    const [paginate, setPaginate] = useState<Paginate>({
        page: 0, size: 20
    })

    const fetchAllPost = async (queryParams: PostQueryParams, paginate: Paginate) => {
        const response = await fetchAllPostApi(queryParams, paginate)
        setPosts(response)
    }

    const updatePaginate = (newPaginate: Paginate) => {
        setPaginate(newPaginate)
        fetchAllPost(params, newPaginate)
    }

    const updateQueryParams = (newQueryParams: PostQueryParams) => {
        setQueryParams(newQueryParams)
        fetchAllPost(newQueryParams, paginate)
    }

    return <Post
        states={{posts, params, paginate}}
        callbacks={{fetchAllPost, updatePaginate, updateQueryParams}}
    ></Post>
}

export default PostContainer