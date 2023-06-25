import {Card} from "primereact/card"
import PostList from "./PostList";
import PostSearch from "./PostSearch";
import { PostCallbacks, PostStates } from "./types";

type PropType = {
    states: PostStates,
    callbacks: PostCallbacks
}

const Post = ({states, callbacks}: PropType) => {
    return <Card title="블로그 글 목록">
        <PostSearch states={states} callbacks={callbacks} />
        <PostList />
    </Card>
}

export default Post;