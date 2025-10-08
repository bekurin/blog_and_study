from typing import Dict, Any
from langgraph.graph import StateGraph, START, END
from pydantic import BaseModel
import time
import random

class DashboardState(BaseModel):
    user_location: str = "서울"
    weather_data: Dict[str, Any] = {}
    news_data: Dict[str, Any] = {}
    stock_date: Dict[str, Any] = {}
    dashboard_report: str = ""
    start_time: float = 0.0
    
def coordinator(state: DashboardState) -> Dict[str, Any]:
    print(f"대시보드 생성 시작 - 위치: {state.user_location}")
    return {"start_time": time.time()}

def weather_checker(state: DashboardState) -> Dict[str, Any]:
    print("날씨 확인 중...")
    time.sleep(random.uniform(1.0, 2.0))
    
    weather_info = {
        "location": state.user_location,
        "condition": "맑음",
        "temperature": 22,
        "humidity": 65
    }
    
    print(f"날씨: {weather_info['condition']}, {weather_info['temperature']}oC")
    return {"weather_data": weather_info}

def news_fetcher(state: DashboardState) -> Dict[str, Any]:
    print("뉴스 수집 중...")
    time.sleep(random.uniform(1.5, 2.5))
    
    news_info = {
        "articles": [
            {"title": "AI 기술 발전 소식", "summary": "AI 분야 새로운 혁신"},
            {"title": "경제 동향 분석", "summary": "글로벌 경제 전망"}
        ],
        "count": 2
    }
    
    print(f"뉴스 {news_info['count']}개 수집 완료")
    return {"news_data": news_info}

def stock_analyzer(state: DashboardState) -> Dict[str, Any]:
    print("주식 분석 중...")
    time.sleep(random.uniform(2.0, 3.0))
    
    stock_info = {
        "KOSPI": {"price": 3500, "change": +3.5},
        "NASDAQ": {"price": 16000, "change": -1.0},
    }
    
    print("주식 분석 완료")
    return {"stock_data": stock_info}

def aggregator(state: DashboardState) -> Dict[str, Any]:
    print("리포트 생성 중...")
    
    parellel_time = time.time() - state.start_time
    
    report = f"""
    대시보드 리포트
    - 날씨:
    - 뉴스:
    - 주식:  
    """