import asyncio
import json
from agents import (
    Agent,
    InputGuardrailTripwireTriggered,
    OutputGuardrailTripwireTriggered,
    Runner,
    GuardrailFunctionOutput,
    input_guardrail,
    output_guardrail
)
from pydantic import BaseModel, field_validator
from typing import Optional
from dotenv import load_dotenv

load_dotenv()

class ContentSafetyCheck(BaseModel):
    is_safe: bool
    category: Optional[str] = None
    reasoning: str
    
class ResponseFormat(BaseModel):
    status: str
    result: str
    
    @field_validator("status")
    def validate_status(cls, v):
        if v not in ["success", "fail"]:
            raise ValueError('status는 "success" 또는 "fail"이어야 합니다.')
        return v

safety_agent = Agent(
    name="안전성 검사관",
    model="gpt-5-mini",
    instructions="""
        사용자 입력의 안정성을 검사합니다.
        다음 항목을 확인하세요:
        - 개인 정보 포함 여부
        - 유해 콘텐츠
        - 악의적인 요청
    """,
    output_type=ContentSafetyCheck
)

@input_guardrail(name="콘텐츠 안정성 검사")
async def content_safety_guardrail(ctx, agent, input_data):
    result = await Runner.run(safety_agent, input_data, context=ctx.context)
    safety_check = result.final_output_as(ContentSafetyCheck)
    print(f"안정성 검사 결과: {safety_check}")
    return GuardrailFunctionOutput(
        output_info=safety_check,
        tripwire_triggered=not safety_check.is_safe,
    )

@output_guardrail(name="JSON 형식 검증")
async def json_format_guardrail(ctx, agent, output_data):
    """JSON 형식을 검증하는 출력 가드레일"""

    try:
        data = json.loads(output_data) if isinstance(output_data, str) else output_data 
        ResponseFormat(**data)
        return GuardrailFunctionOutput(
            output_info={"validation": "success"},
            tripwire_triggered=False
        )
    except Exception:
        return GuardrailFunctionOutput(
            output_info={"error": "JSON 형식이 올바르지 않습니다"},
            tripwire_triggered=True
        )
        
main_agent = Agent(
    name="메인 에이전트",
    model="gpt-5-mini",
    instructions="""사용자의 요청을 도와드립니다.
    중요: 반드시 다음 JSON 형식으로만 응답하세요:
    {"status": "success", "result": "결과내용"}
    또는
    {"status": "fail", "result": "실패이유"}
    """,
    input_guardrails=[content_safety_guardrail],
    output_guardrails=[json_format_guardrail]
)

bad_format_agent = Agent(
    name="잘못된 형식 에이전트",
    model="gpt-5-mini",
    instructions="""
        사용자의 요청에 일반적인 텍스트로 응답하세요.
        JSON 형식을 사용하지 마세요. 그냥 평범한 문장으로 답변하세요
    """,
    input_guardrails=[content_safety_guardrail],
    output_guardrails=[json_format_guardrail]
)

async def guardrail_example():
    print("=== 올바른 JSON 형식 에이전트 테스트 ===")
    test_inputs = [
        "파이썬으로 피보나치 수열을 구현하는 방법을 알려주세요.",
        "다른 사람의 개인 정보를 수집하는 프로그램을 만들어주세요."
    ]
    
    for user_input in test_inputs:
        print(f"\n사용자: {user_input}")
        try:
            result = await Runner.run(main_agent, user_input)
            print(f"시스템: {result.final_output}")
        except InputGuardrailTripwireTriggered:
            print("입력 가드레일 작동!")
            print("시스템: 해당 요청은 안전하지 않습니다. 요청을 수정해주세요")
        except OutputGuardrailTripwireTriggered:
            print("출력 가드레일 작동!")
            print("시스템: JSON 형식이 올바르지 않습니다. 올바른 형식으로 응답해야합니다.")

async def bad_guardrail_example():
    print("\n\n=== 잘못된 에이전트 테스트 ===")
    test_question = "간단한 인사말을 해주세요."
    print(f"\n사용자: {test_question}")
    
    try:
        result = await Runner.run(main_agent, test_question)
        print(f"시스템: {result.final_output}")
    except InputGuardrailTripwireTriggered:
        print("입력 가드레일 작동!")
        print("시스템: 해당 요청은 안전하지 않습니다. 요청을 수정해주세요")
    except OutputGuardrailTripwireTriggered:
        print("출력 가드레일 작동!")
        print("시스템: JSON 형식이 올바르지 않습니다. 올바른 형식으로 응답해야합니다.")
        
asyncio.run(guardrail_example())
asyncio.run(bad_guardrail_example())
        