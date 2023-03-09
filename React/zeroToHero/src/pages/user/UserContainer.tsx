import { useState } from "react";
import type { CallbacksType, StatesType, UserSearchParam, IUser } from "./types";
import userClient from "../../clients/user/UserClient";
import User from "./User";
import { PageResponse } from "../../clients/PageResponse";
import { PageType } from "../../components/Pagination";

const UserContainer = () => {
    const [pagedUsers, setPagedUsers] = useState<PageResponse<IUser>>()

    const fetchPagedUsers = async (searchParam: UserSearchParam, pageType: PageType) => {
        const pagedUsers = await userClient().fetchPagedUsers(searchParam, pageType)
        console.log(pagedUsers)
        setPagedUsers(pagedUsers)
    }

    if (pagedUsers === undefined) throw Error("")
    
    const callbacks: CallbacksType = {fetchPagedUsers}
    const states: StatesType = {pagedUsers}
    return <User callbacks={callbacks} states={states} />
}

export default UserContainer