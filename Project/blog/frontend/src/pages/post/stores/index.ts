import { PostCallbacks, PostQueryParams, PostStates } from "../types";
import { useState } from "react";
import { PageResponse } from "../../../apis/PageResponse";
import { IPost } from "../types";
import { create } from "zustand";
import fetchAllPostApi from "../../../apis/post/fetchAllPostApi";

const usePostStore = create<PostStates & PostCallbacks>((set) => ({
    posts: null,
    params: {title:""},
    paginate: {page: 0, size: 20},
    fetchAllPost: async (queryParams, paginate) => {
        const response = await fetchAllPostApi(queryParams, paginate)
        set(() => ({posts: response}))
    },
    updatePaginate: (newPaginate) => {
        set(() => ({paginate: newPaginate}))
    },
    updateQueryParams: (newQueryParams) => {
        set(() => ({params: newQueryParams}))
    }
}))


export default usePostStore
