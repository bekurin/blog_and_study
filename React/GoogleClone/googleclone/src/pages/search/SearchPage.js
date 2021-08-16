import React from "react";
import "./SearchPage.css";
import { useStateValue } from "../../components/StateProvider";
import useGoogleSearch from "../../useGoogleSearch";
import Response from "../../response";
import { Link } from "react-router-dom";
import Search from "../../components/Search";
import SearchIcon from "@material-ui/icons/Search";
import DescriptionIcon from "@material-ui/icons/Description";
import ImageIcon from "@material-ui/icons/Image";
import ShopIcon from "@material-ui/icons/Shop";
import MapIcon from "@material-ui/icons/Map";
import MoreIcon from "@material-ui/icons/More";

const SearchPage = () => {
  const [{ term }, dispatch] = useStateValue();

  //Live API Call Super Powerful Live API Code
  // const { data } = useGoogleSearch(term);

  const data = Response;

  console.log(data);
  return (
    <div className="searchPage">
      <div className="searchPage_header">
        <Link to="/">
          <img
            className="searchPage_logo"
            src="https://i2.wp.com/kr.hypebeast.com/files/2018/09/google-logos-2018-5.png?w=1600"
            alt="Google Logo"
          />
        </Link>
        <div className="searchPage_headerBody">
          <Search hideButtons />
          <div className="searchPage_options">
            <SearchIcon />
            <div className="searchPage_optionsLeft">
              <div className="searchPage_option">
                <Link to="/all">전체</Link>
              </div>
              <div className="searchPage_option">
                <DescriptionIcon />
                <Link to="/news">뉴스</Link>
              </div>
              <div className="searchPage_option">
                <ImageIcon />
                <Link to="/images">이미지</Link>
              </div>
              <div className="searchPage_option">
                <ShopIcon />
                <Link to="/shopping">쇼핑</Link>
              </div>
              <div className="searchPage_option">
                <MapIcon />
                <Link to="/maps">지도</Link>
              </div>
              <div className="searchPage_option">
                <MoreIcon />
                <Link to="/more">더보기</Link>
              </div>
            </div>
            <div className="searchPage_optionsRight">
              <div className="searchPage_option">
                <Link to="/settings">설정</Link>
              </div>
              <div className="searchPage_option">
                <Link to="/tools">도구</Link>
              </div>
            </div>
          </div>
        </div>
      </div>
      {term && (
        <div className="searchPage_results">
          <p className="searchPage_resultCount">
            검색결과 약 {data?.searchInformation.formattedTotalResults} (
            {data?.searchInformation.formattedSearchTime}s)
          </p>

          {data?.items.map((item) => (
            <div className="searchPage_result">
              <a className="searchPage_resultLink" href={item.link}>
                {item.pagemap?.cse_image?.length > 0 &&
                  item.pagemap?.cse_image[0]?.src && (
                    <img
                      className="searchPage_resultImage"
                      src={item.pagemap?.cse_image[0]?.src}
                    />
                  )}
                {item.displayLink}
              </a>
              <a className="searchPage_resultTitle" href={item.link}>
                <h2>{item.title}</h2>
              </a>
              <p className="searchPage_resultSnippet">{item.snippet}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default SearchPage;
