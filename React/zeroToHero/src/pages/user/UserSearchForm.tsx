import { useEffect, useState } from "react"
import { CallbacksType, StatesType } from "./UserContainer"


type PropsType = {
    callbacks: CallbacksType
}

const UserSearchForm = ({callbacks}: PropsType) => {
    const [formData, setFormData] = useState<FormData>()

    const handleFormSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const data = new FormData(event.currentTarget)
        setFormData(data)
        console.log(formData)
    }

    return (
        <>
            <form onSubmit={handleFormSubmit}>
                <label htmlFor="username">회원 이름: </label>
                <input type="text" className="form-group" id="username"/>
                <button type="submit">검색하기</button>
            </form>
        </>
    )
}

export default UserSearchForm;