import { CallbacksType, StatesType } from "./UserContainer"
import UserSearchForm from "./UserSearchForm"
import UserList from "./UserList"
import Pagination from "../../components/Pagination"
import { useState, useEffect } from "react"


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
            <Pagination 
                activePage={activePage}
                setActivePage={setActivePage}
                pageType={states.page}
            />
        </>
    )
}

export default User;