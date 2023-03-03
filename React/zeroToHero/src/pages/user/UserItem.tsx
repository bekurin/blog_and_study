import { UserType } from "./UserContainer"

type PropsType = {
    user: UserType
}

const UserItem = ({user}: PropsType) => {
    return (
        <>
            <tr>
                <td>{user.id}</td>
                <td>{user.username}</td>
                <td>{user.email}</td>
            </tr>
        </>
    )
}

export default UserItem