import UserSearchForm from "./UserSearchForm"
import UserList from "./UserList"
import { useState, useEffect } from "react"
import Pagination, { PageType } from "../../components/Pagination"
import type { StatesType, CallbacksType, UserSearchParam } from "./types"


type PropsType = {
    states: StatesType,
    callbacks: CallbacksType
}

const User = ({states, callbacks}: PropsType) => {
    const [pageType, setPageType] = useState<PageType>({page: 0, size:20})
    const [searchParam, setSearchParam] = useState<UserSearchParam>({username: ""})

    useEffect(() => {
        setPageType({page: 0, size: 20})
    }, [searchParam])

    useEffect(() => {
        callbacks.fetchPagedUsers(searchParam, pageType)
    }, [pageType])

    return (
        <>
            <UserSearchForm setSearchParam={setSearchParam} />
            <UserList callbacks={callbacks} states={states}/>
            <Pagination totalRecord={states.pagedUsers.totalPage} />
        </>
    )
}

export default User;