import json
import re
import asyncio
from typing import Optional

import httpx
import feedparser
import trafilatura
from bs4 import BeautifulSoup
from utils import convert_gmt_to_kst
from state import NewsState

GOOGLE_NEWS_BASE_URL = "https://news.google.com"
GOOGLE_NEWS_API_URL = f"{GOOGLE_NEWS_BASE_URL}/_/DotsSplashUi/data/batchexecute"
KOREA_PARAMS = "&hl=ko&gl=KR&ceid=KR:ko"


class RSSCollectorAgent:
    """RSS 피드를 수집하는 에이전트"""

    def __init__(self):
        self.name = "RSS Collector"
        self.rss_url = f"{GOOGLE_NEWS_BASE_URL}/rss?{KOREA_PARAMS[1:]}"
        self.feed = None

    def load_feed(self) -> None:
        """RSS 피드를 로드합니다."""
        self.feed = feedparser.parse(self.rss_url)

    @staticmethod
    def extract_chosun_content(html_content):
        """조선일보 기사 내용을 특별 처리합니다."""
        pattern = r"Fusion\.globalContent\s*=\s*({.*?});"
        match = re.search(pattern, html_content, re.DOTALL)

        if match:
            try:
                content_data = json.loads(match.group(1))
                texts = []
                if "content_elements" in content_data:
                    for element in content_data["content_elements"]:
                        if element.get("type") == "text" and "content" in element:
                            texts.append(element["content"])
                return "\n\n".join(texts)
            except json.JSONDecodeError:
                pass
        return ""

    async def extract_article_url(self, google_news_url: str) -> Optional[str]:
        """
        참조: https://stackoverflow.com/questions/79388897/how-to-scrape-google-rssfeed-links/79388987#79388987
        Google News는 JavaScript를 사용하여 페이지를 리다이렉션 시키므로 내부 API를 직접 호출하여 우회
        """
        async with httpx.AsyncClient() as client:
            try:
                response = await client.get(google_news_url)
                soup = BeautifulSoup(response.text, "html.parser")

                data_element = soup.select_one("c-wiz[data-p]")
                if not data_element:
                    return None

                raw_data = data_element.get("data-p")
                json_data = json.loads(raw_data.replace(  # type: ignore
                    "%.@.", '["garturlreq",'))
                payload = {
                    "f.req": json.dumps(
                        [
                            [
                                [
                                    "Fbv4je",
                                    json.dumps(
                                        json_data[:-6] + json_data[-2:]),
                                    "null",
                                    "generic",
                                ]
                            ]
                        ]
                    )
                }

                headers = {
                    "content-type": "application/x-www-form-urlencoded;charset=UTF-8",
                    "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
                }

                api_response = await client.post(
                    GOOGLE_NEWS_API_URL, headers=headers, data=payload
                )
                cleaned_response = api_response.text.replace(")]}'", "")
                response_data = json.loads(cleaned_response)
                article_url = json.loads(response_data[0][2])[1]
                return article_url
            except Exception:
                return None

    async def parse_entry(self, entry) -> dict[str, Optional[str]]:
        """RSS 피드 항목을 파싱합니다."""
        google_news_url = entry.link + KOREA_PARAMS

        original_url = await self.extract_article_url(google_news_url)
        content = ""

        if original_url:
            downloaded = trafilatura.fetch_url(original_url)

            if downloaded:
                if (
                    "m.health.chosun.com" not in original_url
                    and "chosun.com" in original_url
                ):
                    content = self.extract_chosun_content(downloaded)
                else:
                    content = trafilatura.extract(
                        downloaded,
                        include_comments=False,
                        include_images=False,
                        include_links=False,
                        target_language="ko",
                    )

        return {
            "title": entry.title,
            "published_kst": convert_gmt_to_kst(entry.published),
            "source": entry.source.get("title", "Unknown"),
            "google_news_url": google_news_url,
            "original_url": original_url,
            "content": content or "",
        }

    async def collect_rss(self, state: NewsState) -> NewsState:
        """RSS 피드를 수집하고 상태를 업데이트합니다."""
        print("--- RSS 피드 수집 시작 ---")

        try:
            if not self.feed:
                self.load_feed()

            tasks = [self.parse_entry(entry)
                     for entry in self.feed.entries]  # type: ignore
            raw_news = await asyncio.gather(*tasks)

            state.raw_news = raw_news
            print(f"총 {len(raw_news)}개의 뉴스 기사 수집 완료")

        except Exception as e:
            print(f"RSS 피드 수집 중 오류 발생: {e}")
            state.error_log.append(f"RSSCollectorAgent: {str(e)}")

        return state
