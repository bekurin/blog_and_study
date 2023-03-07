export interface PageResponse<T> {
    hasNext: boolean,
    hasPrevious: boolean,
    page: number,
    size: number,
    totalPage: number,
    contents: T[]
}