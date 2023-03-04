import axios from "axios";
import { useEffect, useState } from "react";
import { PageType } from "../../components/Pagination";
import User from "./User";

export type UserType = {
    id: number, 
    username: string,
    email: string
}
export type StatesType = {userList: Array<UserType>, page: PageType}
export type CallbacksType = {
    fetchUserList: () => void;
}

const BASE_URL = "http://localhost:4000/users"
const UserContainer = () => {
    const [userList, setUserList] = useState<Array<UserType>>([])
    const [page, setPage] = useState<PageType>({hasNext:false, hasPrevious:false, size:10, page:0})

    useEffect(() => {
        fetchUserList();
    }, []);

    const fetchUserList = async () => {
        try {
            const response = await axios.get(BASE_URL)
            setUserList(response.data.contents)
            setPage(response.data)
        } catch (e) {
            if (e instanceof Error) console.error(e.message)
            else console.error(e)
        }
    }
    const callbacks: CallbacksType = {fetchUserList}
    const states: StatesType = {userList, page}
    return <User callbacks={callbacks} states={states} />
}

export default UserContainer