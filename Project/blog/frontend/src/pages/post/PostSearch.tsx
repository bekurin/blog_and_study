import usePostStore from "./stores"
import {InputText} from "primereact/inputtext"
import {Button} from "primereact/button"

const PostSearch = () => {
    const {updateQueryParams} = usePostStore()
    return (
        <div>
            <InputText placeholder="Username" />
            <Button>검색하기</Button>
        </div>
    )
}

export default PostSearch