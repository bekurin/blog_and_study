import { Paginator, PaginatorPageChangeEvent } from "primereact/paginator";
import { Paginate } from "./types";

type PropsType = {
  totalRecords: number | undefined;
  paginate: Paginate;
  setPaginate: (pageType: Paginate) => void;
};

const Pagination = ({
  totalRecords,
  paginate: pageType,
  setPaginate: setPageType,
}: PropsType) => {
  const onPageChange = (event: PaginatorPageChangeEvent) => {
    setPageType({ size: event.rows, page: event.page });
  };

  return (
    <div className="card">
      <Paginator
        first={pageType.page * pageType.size}
        rows={pageType.size}
        totalRecords={totalRecords}
        rowsPerPageOptions={[20, 30, 50]}
        onPageChange={onPageChange}
      />
    </div>
  );
};

export default Pagination;