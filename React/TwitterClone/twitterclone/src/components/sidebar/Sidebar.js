import React from "react";
import "./Sidebar.css";
import SidebarOption from "./SidebarOption";
import TwitterIcon from "@material-ui/icons/Twitter";
import HomeIcon from "@material-ui/icons/Home";
import SearchIcon from "@material-ui/icons/Search";
import NotificationsNoneIcon from "@material-ui/icons/NotificationsNone";
import MailOutlineIcon from "@material-ui/icons/MailOutline";
import BookmarkBorderIcon from "@material-ui/icons/BookmarkBorder";
import ListAltIcon from "@material-ui/icons/ListAlt";
import PermIdentityIcon from "@material-ui/icons/PermIdentity";
import MoreHorizIcon from "@material-ui/icons/MoreHoriz";
import { Button } from "@material-ui/core";

const Sidebar = () => {
  return (
    <div className="sidebar">
      <TwitterIcon className="sidebar__twittweIcon" />

      <SidebarOption active Icon={HomeIcon} text="홈" />
      <SidebarOption Icon={SearchIcon} text="검색" />
      <SidebarOption Icon={NotificationsNoneIcon} text="알림" />
      <SidebarOption Icon={MailOutlineIcon} text="메세지" />
      <SidebarOption Icon={BookmarkBorderIcon} text="북마크" />
      <SidebarOption Icon={ListAltIcon} text="목록" />
      <SidebarOption Icon={PermIdentityIcon} text="프로필" />
      <SidebarOption Icon={MoreHorizIcon} text="" />

      <Button variant="outlined" className="sidebar_tweet" fullWidth>
        트윗
      </Button>
    </div>
  );
};

export default Sidebar;
