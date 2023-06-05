import axios from "axios";
import { IPost, PostQueryParams } from "../../pages/post/types";
import { PageResponse } from "../PageResponse";
import { Paginate } from "../../pagination/types";

const fetchAllPostApi = async (params: PostQueryParams, paginate: Paginate): Promise<PageResponse<IPost>> => {
    return await axios.get(`/v1/posts/page/${paginate.page}`, {
        params: {
            ...params,
            size: paginate.size
        }
    })
}

export default fetchAllPostApi