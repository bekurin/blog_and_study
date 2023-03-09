import axios from "axios";
import { PageType } from "../../components/Pagination";
import SERVER_URL from "../../constants/urls";
import { UserSearchParam } from "../../pages/user/types";
import { PageResponse } from "../PageResponse";

export type UserType = {
    id: number,
    username: string,
    email: string
}

type UserClientType = {
    fetchPagedUsers: (searchParam: UserSearchParam, pageType: PageType) => Promise<PageResponse<UserType>>
}

const userClient = (): UserClientType => {
    const fetchPagedUsers = async (searchParam: UserSearchParam, pageType: PageType) => {
        console.log(SERVER_URL.USERS)
        const url = new URLSearchParams(`${SERVER_URL.USERS}/${pageType.page}`)
        console.log(url)
        const response = await axios.get(SERVER_URL.USERS)
        return response.data
    }
    return {fetchPagedUsers}
}

export default userClient;