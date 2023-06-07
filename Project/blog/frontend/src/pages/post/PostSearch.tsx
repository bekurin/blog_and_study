import usePostStore from "./stores"
import {InputText} from "primereact/inputtext"
import {Button} from "primereact/button"
import { FormEvent } from "react"

const PostSearch = () => {
    const {updateQueryParams} = usePostStore()

    const handleSubmitAction = (event: FormEvent) => {
        event.preventDefault()
        console.log(event.currentTarget)
    }

    return (
        <form onSubmit={handleSubmitAction}>
            <input name="title" type="text" placeholder="제목을 입력해주세요" />
            <Button type="submit">검색하기</Button>
        </form>
        )
}

export default PostSearch