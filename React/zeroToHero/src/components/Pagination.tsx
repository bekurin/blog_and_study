
export type PageType = {
    hasNext: boolean,
    hasPrevious: boolean,
    page: number,
    size: number
}

type PropsType = {
    pageType: PageType
}

const Pagination = ({pageType}: PropsType) => {
    return (
        <>
        <div>
            <div>이전</div>
                {
                    pageType.page
                },
                {
                    pageType.size
                }
            <div>이후</div>
        </div>
        </>
    )
}

export default Pagination;