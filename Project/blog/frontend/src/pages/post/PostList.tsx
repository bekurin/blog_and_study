import usePostStore from "./stores"
import {DataTable} from "primereact/datatable"
import { Column } from "primereact/column"

const PostList = () => {
    const {posts} = usePostStore()

    return <DataTable value={posts?.contents}>
        <Column>haha</Column>
    </DataTable>
}

export default PostList