import { PageResponse } from "../../../clients/PageResponse";
import { PageType } from "../../../components/Pagination";

export type StatesType = {pagedUsers: PageResponse<UserType>}
export type CallbacksType = {
    fetchPagedUsers: (searchParam: UserSearchParam, pageType: PageType) => void;
}
export type UserType = {
    id: number, 
    username: string,
    email: string
}

export type UserSearchParam = {
    username: string
}