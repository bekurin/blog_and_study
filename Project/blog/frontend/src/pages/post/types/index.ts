import { PageResponse } from "../../../apis/PageResponse";
import { Paginate } from "../../../pagination/types";

export interface PostStates {
    posts: PageResponse<IPost> | null
    params: PostQueryParams
    paginate: Paginate
}

export interface PostCallbacks {
    fetchAllPost: (queryParams: PostQueryParams, paginate: Paginate) => void;
    updatePaginate: (newPaginate: Paginate) => void;
    updateQueryParams: (newQueryParams: PostQueryParams) => void;
}

export interface IPost {
    id: number,
    title: string,
    summary: string,
}

export interface PostQueryParams {
    title: string
}