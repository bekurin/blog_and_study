import {DataTable} from "primereact/datatable"
import { Column } from "primereact/column"
import { PostCallbacks, PostStates } from "./types"

type PropType = {
    states: PostStates,
    callbacks: PostCallbacks
}

const PostList = () => {
    return <DataTable>
        <Column>haha</Column>
    </DataTable>
}

export default PostList