from typing import Annotated, Any
from pydantic import BaseModel, ConfigDict
from langchain_core.messages import BaseMessage
from langgraph.graph.message import add_messages

class NewsState(BaseModel):
    model_config = ConfigDict(arbitrary_types_allowed=True)
    messages: Annotated[list[BaseModel], add_messages] = []
    raw_news: list[dict[str, Any]] = []
    summarized_news: list[dict[str, Any]] = []
    categorized_news: dict[str, list[dict[str, Any]]] = {}
    final_report: str = ""
    error_log: list[str] = []