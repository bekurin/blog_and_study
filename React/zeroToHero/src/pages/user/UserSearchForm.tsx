import { useEffect, useState } from "react"
import { UserSearchParam } from "./types"


type PropsType = {
    setSearchParam: React.Dispatch<React.SetStateAction<UserSearchParam>>
}

const UserSearchForm = ({setSearchParam}: PropsType) => {
    const [formData, setFormData] = useState<FormData>()

    const handleFormSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const data = new FormData(event.currentTarget)
        setFormData(data)
        console.log(formData)
    }

    return (
            <form onSubmit={handleFormSubmit}>
                <label htmlFor="username">회원 이름: </label>
                <input type="text" className="form-group" id="username"/>
                <button type="submit">검색하기</button>
            </form>
    )
}

export default UserSearchForm;