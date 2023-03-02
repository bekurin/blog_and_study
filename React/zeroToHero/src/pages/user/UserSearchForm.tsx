import { CallbacksType, StatesType } from "./UserContainer"


type PropsType = {
    callbacks: CallbacksType
}

const UserSearchForm = ({callbacks}: PropsType) => {
    return (
        <>
            <form>
                <label></label>
                <input></input>
            </form>
        </>
    )
}

export default UserSearchForm;