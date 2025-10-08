from mcp.server.fastmcp import FastMCP

mcp = FastMCP("Simple MCP Server")

@mcp.tool()
def hello(name: str = "general") -> str:
    return f"안녕하세요 {name}님"

@mcp.tool()
def get_prompt(prompt_type: str = "general") -> str:
    prompts = {
        "general": "당신은 도움이 된느 AI 어시스턴트입니다. 사용자의 질문에 정확하고 친절하게 답변해주세요.",
        "code_review": "다음 코드를 검토하고 개선점을 제안해주세요. 코드의 가독성, 성능, 보안 측면을 고려해주세요.",
        "translate": "다음 텍스트를 자연스러운 한국어로 번역해주세요.",
        "summarize": "다음 내용을 핵심 포인트 중심으로 간결하게 요약해주세요."
    }
    return prompts.get(prompt_type, prompts["general"])

@mcp.resource("simple://info")
def get_server_info():
    return """
    Simple MCP Server 정보
    ====================
    
    이 서버는 MCP(Model Context Protocol)의 기본 기능을 시연하는 간단한 예제입니다.
    
    제공하는 도구:
    - hello: 인사말 생성
    - get_prompt: 프롬프트 템플릿 제공
    
    제공하는 리소스:
    - simple://info: 서버 정보
"""

if __name__ == "__main__":
    mcp.run(transport="streamable-http")