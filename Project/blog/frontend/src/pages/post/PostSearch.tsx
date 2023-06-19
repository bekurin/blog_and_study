import {InputText} from "primereact/inputtext"
import {Button} from "primereact/button"
import { FormEvent } from "react"

const PostSearch = () => {
    const handleSubmitAction = (event: FormEvent) => {
        event.preventDefault()
        console.log(event.currentTarget.title.value)
    }

    return (
        <form className="" onSubmit={handleSubmitAction}>
            <div className="form-group">
                <label id="title">제목</label>
                <input className="form-control" id="title" name="title" type="text" placeholder="제목을 입력해주세요" />
            </div>
            <Button className="btn btn-primary" type="submit">검색하기</Button>
        </form>
        )
}

export default PostSearch
