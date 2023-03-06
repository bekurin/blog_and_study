import axios from "axios";
import SERVER_URL from "../../constants/urls";

export type UserType = {
    id: number,
    username: string,
    email: string
}

type UserClientType = {
    getById: (userId: number) => Promise<UserType>
}

const userClient = (): UserClientType => {
    const getById = async (userId: number): Promise<UserType> => {
        try {
            const response = await axios.get(SERVER_URL.USERS)
            return response.data as UserType
        } catch (e) {
            if (e instanceof Error) {
                alert(`error: ${e.message}`)
            }
            else console.error(e)
        }
        throw new Error(`cannot find the user id=${userId}`);
    }
    return {getById}
}

export default userClient;