import axios from "axios";
import SERVER_URL from "../../constants/urls";
import { PageResponse } from "../PageResponse";

export type UserType = {
    id: number,
    username: string,
    email: string
}

type UserClientType = {
    fetchPagedUsers: () => Promise<PageResponse<UserType>>
}

const userClient = (): UserClientType => {
    const fetchPagedUsers = async (): Promise<PageResponse<UserType>> => {
        const response = await axios.get(SERVER_URL.USERS)
        return response.data
    }
    return {fetchPagedUsers}
}

export default userClient;