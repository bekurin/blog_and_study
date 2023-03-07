import { PageResponse } from "../../../clients/PageResponse";

export type StatesType = {pagedUsers: PageResponse<UserType>}
export type CallbacksType = {
    fetchPagedUsers: (userId: number) => void;
}
export type UserType = {
    id: number, 
    username: string,
    email: string
}