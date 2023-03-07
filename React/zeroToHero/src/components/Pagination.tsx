import { Paginator, PaginatorPageChangeEvent } from 'primereact/paginator';
import { useState } from 'react';

export type PageType = {
    page: number,
    size: number,
}

type PropsType = {
    totalRecord: number
}

const Pagination = ({totalRecord}: PropsType) => {
    const [pageType, setPageType] = useState<PageType>({page: 0, size:20})

    const onPageChange = (event: PaginatorPageChangeEvent) => {
        setPageType({page: event.page, size: event.rows})
    }

    return (
        <Paginator 
            rows={pageType.size}
            rowsPerPageOptions={[20, 30, 50]}
            totalRecords={totalRecord}
            onPageChange={onPageChange}
        />
    )
}

export default Pagination