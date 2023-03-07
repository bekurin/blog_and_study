import UserSearchForm from "./UserSearchForm"
import UserList from "./UserList"
import { useState, useEffect } from "react"
import Pagination from "../../components/Pagination"
import type { StatesType, CallbacksType } from "./types"


type PropsType = {
    states: StatesType,
    callbacks: CallbacksType
}

const User = ({states, callbacks}: PropsType) => {
    const [activePage, setActivePage] = useState(0)

    useEffect(() => {
        console.log(activePage)
    })

    return (
        <>
            <UserSearchForm callbacks={callbacks} />
            <UserList callbacks={callbacks} states={states}/>
            <Pagination totalRecord={states} />
        </>
    )
}

export default User;