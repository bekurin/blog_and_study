from a2a.types import AgentCapabilities, AgentCard, AgentSkill

def create_agent_card() -> AgentCard:
    greeting_skill = AgentSkill(
        id="basic_greeting",
        name="Basic Greeting",
        description="간단한 인사와 기본적인 대화를 제공합니다.",
        tags=["greeting", "hello", "basic"],
        examples=["안녕하세요", "hello", "hi", "고마워요"],
        input_modes=["text"],
        output_modes=["text"]
    )
    
    agent_card = AgentCard(
        name="Basic Hello World Agent",
        description="A2A examples",
        url="http://localhost:9999",
        version="1.0.0",
        default_input_modes=["text"],
        default_output_modes=["text"],
        capabilities=AgentCapabilities(streaming=True),
        skills=[greeting_skill],
        supports_authenticated_extended_card=False
    )
    return agent_card

def main():
    agent_card = create_agent_card()
    print(agent_card.model_dump_json())

if __name__ == "__main__":
    main()