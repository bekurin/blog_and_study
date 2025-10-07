import random
from typing import Any, Dict, Literal
from langchain_openai import ChatOpenAI
from langgraph.graph import StateGraph, START, END
from pydantic import BaseModel, Field
from langchain_core.messages import SystemMessage, HumanMessage
from dotenv import load_dotenv

load_dotenv()

class EmotionBotState(BaseModel):
    user_message: str = Field(default="", description="사용자 입력 메시지")
    emotion: str = Field(default="", description="분석된 감정")
    response: str = Field(default="", description="최종 응답 메시지")
    
llm = ChatOpenAI(model="gpt-5-mini", max_completion_tokens=10)

def analyze_emotion(state: EmotionBotState) -> Dict[str, Any]:
    message = state.user_message
    print(f"LLM 감정 분석 중: '{message}")
    
    messages = [
        SystemMessage(
            content="당신은 감정 분석 전문가입니다. 사용자의 메시지를 분석하여 'positive', 'negative', 'neutral' 중 하나로 감정을 분류해주세요. 답변은 반드시 하나의 단어만 출력하세요.",
        ),
        HumanMessage(
            content=f"다음 메시지의 감정을 분석해주세요: {message}"
        )
    ]
    
    
    response = llm.invoke(messages)
    emotion = response.content.strip().lower() # type: ignore
    
    if emotion not in ["positive", "negative", "neutral"]:
        emotion = "neutral"
    
    print(f"LLM 감정 분석 결과: {emotion}")
    return {"emotion": emotion}

def generate_positive_response(state: EmotionBotState) -> Dict[str, Any]:
    responses = ["정말 좋은 소식이군요", "기분이 좋으시군요", "멋지네요", "좋아요"]
    return {"response": random.choice(responses)}

def generate_negative_response(state: EmotionBotState) -> Dict[str, Any]:
    responses = ["힘든 시간이군요", "마음이 아프시겠어요", "더 좋은 날이 있겠죠"]
    return {"response": random.choice(responses)}
    
def generate_neutral_response(state: EmotionBotState) -> Dict[str, Any]:
    responses = ["감사해요, 더 자세하게 말씀해주세요", "흥미롭네요", "이해했습니다. 혹시 도움 필요하시다면 말씀주세요."]
    return {"response": random.choice(responses)}

def route_by_emotion(state: EmotionBotState) -> Literal["positive_response", "negative_response", "neutral_response"]:
    emotion = state.emotion
    print(f"라우팅: {emotion}")
    
    if emotion == "positive":
        return "positive_response"
    if emotion == "negative":
        return "negative_response"
    return "neutral_response"

def create_emotion_bot_graph():
    workflow = StateGraph(EmotionBotState)
    
    workflow.add_node("analyze_emotion", analyze_emotion)
    workflow.add_node("positive_response", generate_positive_response)
    workflow.add_node("negative_response", generate_negative_response)
    workflow.add_node("neutral_response", generate_neutral_response)
    
    workflow.add_edge(START, "analyze_emotion")
    workflow.add_conditional_edges(
        "analyze_emotion",
        route_by_emotion,
        {
            "positive_response": "positive_response",
            "negative_response": "negative_response",
            "neutral_response": "neutral_response",
        }
    )
    
    workflow.add_edge("positive_response", END)
    workflow.add_edge("negative_response", END)
    workflow.add_edge("neutral_response", END)
    
    return workflow.compile()

def main():
    print("=== 감정 분석 챗 봇 테스트 ===\n")
    app = create_emotion_bot_graph()

    test_cases = [
        "오늘 정말 기분이 좋아요",
        "너무 슬퍼요",
        "오늘 날씨 어떄요?"
    ]
    
    for i, message in enumerate(test_cases, 1):
        print(f"테스트 {i}: '{message}'")
        state = EmotionBotState(user_message=message)
        result = app.invoke(state)
        print(f"응답: {result['response']}\n")
    
    mermaid_png = app.get_graph().draw_mermaid_png()
    with open("./images/conditional_routing.png", "wb") as f:
        f.write(mermaid_png)
        

if __name__ == "__main__":
    main()