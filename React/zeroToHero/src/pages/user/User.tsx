import { CallbacksType, StatesType } from "./UserContainer"
import UserSearchForm from "./UserSearchForm"
import UserList from "./UserList"


type PropsType = {
    states: StatesType,
    callbacks: CallbacksType
}

const User = ({states, callbacks}: PropsType) => {
    return (
        <>
            <UserSearchForm callbacks={callbacks} />
            <UserList callbacks={callbacks} states={states}/>
        </>
    )
}

export default User;