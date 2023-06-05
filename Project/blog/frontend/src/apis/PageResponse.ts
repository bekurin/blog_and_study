export interface PageResponse<T> {
    totalContents: number;
    nextPage: number;
    hasNext: boolean;
    hasPrevious: boolean
    contents: T[]
}