from typing import Dict, Any
from langgraph.graph import StateGraph, START, END
from pydantic import BaseModel, Field
from dotenv import load_dotenv

load_dotenv()

class WorkflowStep:
    GREETING = "GREETING"
    PROCESSING = "PROCESSING"
    
class GraphState(BaseModel):
    name: str = Field(default="", description="사용자 이름")
    greeting: str = Field(default="", description="생성된 인사말")
    processed_message: str = Field(default="", description="처리된 최종 메시지")

def generate_greeting(state: GraphState) -> Dict[str, Any]:
    name = state.name or "홍길동"
    greeting = f"안녕하세요, {name}님"
    print(f"[generate_greeting] 인사말 생성: {greeting}")
    return {"greeting": greeting}

def process_message(state: GraphState) -> Dict[str, Any]:
    greeting = state.greeting
    processed_message = f"{greeting} LangGraph에 오신 것을 환영합니다."
    print(f"[process_message] 최종 메시지: {processed_message}")
    return {"processed_message": processed_message}

def create_hello_graph():
    worlflow = StateGraph(GraphState)
    
    worlflow.add_node(WorkflowStep.GREETING, generate_greeting)
    worlflow.add_node(WorkflowStep.PROCESSING, process_message)
    
    worlflow.add_edge(START, WorkflowStep.GREETING)
    
    worlflow.add_edge(WorkflowStep.GREETING, WorkflowStep.PROCESSING)
    worlflow.add_edge(WorkflowStep.PROCESSING, END)
    
    app = worlflow.compile()
    return app

def main():
    print("=== Hello 랭그래프 ===")
    app = create_hello_graph()

    initial_state = GraphState(name="의성", greeting="", processed_message="")
    print("초기 상태", initial_state.model_dump())
    print("\n --- 그래프 실행 시작 ---")
    
    final_state = app.invoke(initial_state)
    
    print("\n --- 그래프 실행 종료 ---")
    print("최종 상태", final_state)
    print(f"\n결과 메시지: {final_state['processed_message']}")
    app.get_graph().draw_ascii()

if __name__ == "__main__":
    main()
