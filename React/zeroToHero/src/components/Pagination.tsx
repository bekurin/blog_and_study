import { AxiosResponse } from "axios"

export type PageType = {
    hasNext: boolean,
    hasPrevious: boolean,
    page: number,
    size: number,
    totalPage: number
}

type PropsType = {
    activePage: number,
    setActivePage: React.Dispatch<React.SetStateAction<number>>,
    pageType: PageType
}

export const bindingPageResult = (response: AxiosResponse<any, any>): PageType => {
    const payload = response.data
    return {
        hasNext: payload.hasNext,
        hasPrevious: payload.hasPrevious,
        page: payload.page,
        size: payload.size,
        totalPage: payload.totalPage
    }
}

const Pagination = ({activePage, setActivePage, pageType}: PropsType) => {
    return (
        <>
            <button onClick={() => {setActivePage(activePage - 1)}} >이전</button>
            {
                Array.from({length: pageType.totalPage}, (_, index) => (
                    <button key={index + 1} value={index + 1} onClick={(e) => setActivePage(e.target.value)}>{index + 1}</button>
                ))

            }
            <button onClick={() => {setActivePage(activePage + 1)}} >이후</button>
        </>
    )
}

export default Pagination