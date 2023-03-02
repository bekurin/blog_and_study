import { CallbacksType, StatesType } from "./UserContainer"


type PropsType = {
    states: StatesType,
    callbacks: CallbacksType
}

const User = ({states, callbacks}: PropsType) => {
    return (
        <>
            <UserSearchForm />
            <UserList />
        </>
    )
}

export default User;