import { AxiosResponse } from "axios"
import { useEffect, useState } from "react"

export type PageType = {
    hasNext: boolean,
    hasPrevious: boolean,
    page: number,
    size: number,
    totalPage: number
}

type PropsType = {
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

const Pagination = ({pageType}: PropsType) => {
    const [page, setPage] = useState(0)

    useEffect(() => {
        if (page <= 0) setPage(0)
        if (page >= pageType.totalPage) setPage(pageType.totalPage)
        console.log("call the callback method...")
    }, [page])

    return (
        <>
        <div>
            <button onClick={() => setPage(page-1)} disabled={!pageType.hasPrevious}>이전</button>
            {
                Array.from({length: pageType.totalPage}, (_, index) => (
                    <button key={index + 1} onClick={() => setPage(index+1)}>{index + 1}</button>
                ))
            }
            <button onClick={() => setPage(page+1)} disabled={!pageType.hasNext}>이후</button>
        </div>
        </>
    )
}

export default Pagination;