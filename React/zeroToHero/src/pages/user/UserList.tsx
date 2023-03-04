import { CallbacksType, StatesType } from "./UserContainer"
import UserItem from "./UserItem"


type PropsType = {
    states: StatesType,
    callbacks: CallbacksType
}

const UserList = ({states, callbacks}: PropsType) => {
    console.log(states)
    const userItemList = states.userList.map((user) => {
        return <UserItem user={user} />
    })
    return (
        <>
            <table>
                <thead>
                    <tr>
                        <th>회원 아이디</th>
                        <th>회원 이메일</th>
                        <th>회원 이름</th>
                    </tr>
                </thead>
                <tbody>
                    {userItemList}
                </tbody>
            </table>
        </>
    )
}

export default UserList;