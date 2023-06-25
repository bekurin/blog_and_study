import {InputText} from "primereact/inputtext"
import {Button} from "primereact/button"
import { ChangeEvent, useState } from "react"
import { PostCallbacks, PostQueryParams, PostStates } from "./types"

type PropType = {
    states: PostStates,
    callbacks: PostCallbacks
}

const PostSearch = ({states, callbacks}: PropType) => {
    const [queryParams, setQueryParams] = useState<PostQueryParams>(states.params)

    const handleSubmit = () => {
        callbacks.updateQueryParams(queryParams)
    }

    return (
        <>
            <InputText placeholder="제목을 입력해주세요" value={queryParams?.title} onChange={(event) => setQueryParams({title: event.target.value})}></InputText>
            <Button size="small" type="submit" onClick={handleSubmit}>검색하기</Button>
        </>
    )
}

export default PostSearch
