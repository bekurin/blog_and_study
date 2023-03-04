import { CallbacksType, StatesType } from "./UserContainer"
import UserSearchForm from "./UserSearchForm"
import UserList from "./UserList"
import Pagination from "../../components/Pagination"


type PropsType = {
    states: StatesType,
    callbacks: CallbacksType
}

const User = ({states, callbacks}: PropsType) => {
    return (
        <>
            <UserSearchForm callbacks={callbacks} />
            <UserList callbacks={callbacks} states={states}/>
            <Pagination pageType={states.page} />
        </>
    )
}

export default User;