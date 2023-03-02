import produce from "immer";
import axios from "axios";
import { useEffect, useState } from "react";
import User from "./User";

export type UserType = {
    id: number, 
    username: string,
    email: string
}
export type StatesType = {userList: Array<UserType>}
export type CallbacksType = {
    fetchUserList: () => void;
}

const BASE_URL = "http://localhost:4000/users"
const UserContainer = () => {
    const [userList, setUserList] = useState<Array<UserType>>([])

    useEffect(() => {
        fetchUserList();
    }, []);

    const fetchUserList = async () => {
        try {
            const response = await axios.get(BASE_URL)
            setUserList(response.data)
        } catch (e) {
            if (e instanceof Error) console.error(e.message)
            else console.error(e)
        }
    }
    const callbacks: CallbacksType = {fetchUserList}
    const states: StatesType = {userList}
    return <User callbacks={callbacks} states={states} />
}

export default UserContainer