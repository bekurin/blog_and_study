import { UserType } from "./UserContainer"

const UserItem = (user: UserType) => {
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