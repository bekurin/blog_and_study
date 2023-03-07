import type { StatesType, CallbacksType } from "./types"
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';


type PropsType = {
    states: StatesType,
    callbacks: CallbacksType
}

const userType = [
    ["id", "회원 아이디"],
    ["username", "회원 이름"],
    ["email", "회원 이메일"]
]

const UserList = ({states, callbacks}: PropsType) => {

    const columnList = Object.entries(userType).map((value, key) => {
        return <Column key={key} header={value[1]} field={value[0]} />
    })

    return (
        <DataTable
            value={states.pagedUsers.contents}>
            {columnList}
        </DataTable>
    )
}

export default UserList;