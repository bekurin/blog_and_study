from agents import Agent, Runner
from dotenv import load_dotenv

load_dotenv()
hello_agent = Agent(
    name="HelloAgent",
    instructions="당신은 HelloAgent입니다. 당신의 임무는 '안녕하세요'라고 인사하는 겁니다."
)

result = Runner.run_sync(hello_agent, "일본어로 인사해주세요.")
print(result.final_output)