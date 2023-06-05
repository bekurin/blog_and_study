import { useSearchParams } from "react-router-dom";

export const useQueryParams = <T extends object>(initialQueryParams: T) => {
  const [searchParams, setSearchParams] = useSearchParams();

  const queryParams = (): T => {
    const result = {} as T;
    Object.entries(initialQueryParams).map(([key]) => {
      Object.assign(result, { [key]: searchParams.get(key) });
    });
    return result;
  };

  const setQueryParams = (newQueryParams: T) => {
    Object.entries(newQueryParams).forEach(([key, value]) => {
      searchParams.set(key, `${value}`);
    });
    setSearchParams(searchParams);
  };

  return {
    queryParams: queryParams(),
    setQueryParams,
  };
};