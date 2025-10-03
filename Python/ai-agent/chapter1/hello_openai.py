import os
from dotenv import load_dotenv
from openai import OpenAI

load_dotenv()

api_key = os.environ.get('OPEN_API_KEY')

client = OpenAI(api_key = api_key)

def get_chat_completion(prompt, model="gpt-5-mini"):
    response = client.chat.completions.create(
        model = model,
        messages = [
            {"role": "system", "content": "당신은 친철하고 도움이 되는 AI 비서입니다."},
            {"role": "user", "content": prompt},
        ]
    )
    return response.choices[0].message.content

if __name__ == "__main__":
    user_prompt = input("AI에게 물어볼 질문을 입력하세요: ")
    response = get_chat_completion(user_prompt)
    print("\nAI 응답: ")
    print(response)