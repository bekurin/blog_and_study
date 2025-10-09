from langchain_openai import ChatOpenAI
from langgraph.graph import StateGraph, END

from state import NewsState
from agents.collector import RSSCollectorAgent
from agents.summarizer import NewsSummarizerAgent
from agents.organizer import NewsOrganizerAgent
from agents.reporter import ReportGeneratorAgent


def create_news_workflow(llm: ChatOpenAI):
    """뉴스 처리 워크플로우 생성 - RSS 수집 → AI 요약 → 카테고리 분류 → 보고서 생성"""

    collector = RSSCollectorAgent()  # RSS 피드 수집 전담
    summarizer = NewsSummarizerAgent(llm)  # AI 요약 생성 전담
    organizer = NewsOrganizerAgent(llm)  # 카테고리 분류 전담
    reporter = ReportGeneratorAgent()  # 보고서 작성 전담

    workflow = StateGraph(NewsState)

    workflow.add_node("collect", collector.collect_rss)
    workflow.add_node("summarize", summarizer.summarize_news)
    workflow.add_node("organize", organizer.organize_news)
    workflow.add_node("report", reporter.generate_report)

    workflow.set_entry_point("collect")  # 시작점 설정
    workflow.add_edge("collect", "summarize")  # 수집 → 요약
    workflow.add_edge("summarize", "organize")  # 요약 → 분류
    workflow.add_edge("organize", "report")  # 분류 → 보고서
    workflow.add_edge("report", END)  # 보고서 → 종료

    return workflow.compile()
