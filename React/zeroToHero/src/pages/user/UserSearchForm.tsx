import { useState } from "react"
import { CallbacksType, StatesType } from "./UserContainer"


type PropsType = {
    callbacks: CallbacksType
}

const UserSearchHandler = (event: FormDataEvent) => {
    
}

const UserSearchForm = ({callbacks}: PropsType) => {
    const [username, setUsername] = useState("")
    return (
        <>
            <form>
                <label htmlFor="username">회원 이름: </label>
                <input type="text" className="form-group" id="username" value={username} onChange={(e) => setUsername(e.target.value)}/>
                <button type="submit" />
            </form>
        </>
    )
}

export default UserSearchForm;