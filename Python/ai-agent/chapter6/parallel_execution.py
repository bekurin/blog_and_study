from typing import Dict, Any
from langgraph.graph import StateGraph, START, END
from pydantic import BaseModel
import time
import random

class DashboardState(BaseModel):
    user_location: str = "서울"
    weather_data: Dict[str, Any] = {}
    news_data: Dict[str, Any] = {}
    stock_data: Dict[str, Any] = {}
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
        "humidity": 65,
    }

    print(f"날씨: {weather_info['condition']}, {weather_info['temperature']}°C")
    return {"weather_data": weather_info}

def news_fetcher(state: DashboardState) -> Dict[str, Any]:
    print("뉴스 수집 중...")
    time.sleep(random.uniform(1.5, 2.5))

    news_info = {
        "articles": [
            {"title": "AI 기술 발전 소식", "summary": "AI 분야 새로운 혁신"},
            {"title": "경제 동향 분석", "summary": "글로벌 경제 전망"},
        ],
        "count": 2,
    }

    print(f"뉴스 {news_info['count']}개 수집 완료")
    return {"news_data": news_info}

def stock_analyzer(state: DashboardState) -> Dict[str, Any]:
    print("주식 분석 중...")
    time.sleep(random.uniform(2.0, 3.0))

    stock_info = {
        "KOSPI": {"price": 2650.5, "change": +1.2},
        "NASDAQ": {"price": 15780.3, "change": -0.8},
    }

    print("주식 분석 완료")
    return {"stock_data": stock_info}

def aggregator(state: DashboardState) -> Dict[str, Any]:
    print("리포트 생성 중...")

    parallel_time = time.time() - state.start_time

    report = f"""
    대시보드 리포트
    - 날씨: {state.weather_data.get("condition", "N/A")} {state.weather_data.get("temperature", "N/A")}°C
    - 뉴스: {state.news_data.get("count", 0)}개 기사
    - 주식: KOSPI {state.stock_data.get("KOSPI", {}).get("price", "N/A")}
    - 실행시간: {parallel_time:.1f}초
"""
    print(f"대시보드 완료 ({parallel_time:.1f}초)")
    return {"dashboard_report": report}

def create_graph():
    workflow = StateGraph(DashboardState)

    workflow.add_node("coordinator", coordinator)
    workflow.add_node("weather", weather_checker)
    workflow.add_node("news", news_fetcher)
    workflow.add_node("stock", stock_analyzer)
    workflow.add_node("aggregator", aggregator)

    workflow.add_edge(START, "coordinator")
    # edge를 아래와 같이 수행하면 coordinator에 진입했을 때 연결된 node를 모두 실행한다.
    workflow.add_edge("coordinator", "weather")
    workflow.add_edge("coordinator", "news")
    workflow.add_edge("coordinator", "stock")
    
    # edge를 아래와 같이 수행하면 실행이 모두 완료되면 aggregator를 통하게 할 수 있다.
    workflow.add_edge("weather", "aggregator")
    workflow.add_edge("news", "aggregator")
    workflow.add_edge("stock", "aggregator")
    workflow.add_edge("aggregator", END)

    return workflow.compile()


def main():
    print("=== LangGraph 병렬 실행 예제 ===\n")

    app = create_graph()
    initial_state = DashboardState(user_location="부산")

    print("병렬 실행 시작!")
    result = app.invoke(initial_state)

    print("\n최종 결과:")
    print(result["dashboard_report"])


if __name__ == "__main__":
    main()