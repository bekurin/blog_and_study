import {Card} from "primereact/card"
import PostList from "./PostList";
import PostSearch from "./PostSearch";

const Post = () => {
    return <Card title="블로그 글 목록">
        <PostSearch />
        <PostList />
    </Card>
}

export default Post;