using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TalkManager : MonoBehaviour
{
    Dictionary<int, string[]> talkData;
    Dictionary<int, Sprite> portraitDate;

    public Sprite[] portraitSprite;

    void Start()
    {
        talkData = new Dictionary<int, string[]>();
        portraitDate = new Dictionary<int, Sprite>();
        MakeData();
    }

    void MakeData()
    {
        talkData.Add(1000, new string[] { "안녕?", "이 곳에 처음 왔구나" });
        talkData.Add(2000, new string[] { "처음 보는 얼굴인데", "누구야??" });

        talkData.Add(10 + 1000, new string[] { "넌 뭐야", "오른쪽으로 가봐" });
        talkData.Add(11 + 2000, new string[] { "넌 뭐야", "할거 없어보이는데", "내 증기 좀 찾아줘" });

        talkData.Add(20 + 5000, new string[] { "난 증기야", "날 잡아봐" });
        talkData.Add(21 + 2000, new string[] { "뭐야", "증기를 찾아달랬지 왜 잡아왔어" });

        portraitDate.Add(1000, portraitSprite[0]);
        portraitDate.Add(2000, portraitSprite[1]);
    }

    public string GetTalk(int id, int talkIndex)
    {
        if (!talkData.ContainsKey(id))
        {
            if(!talkData.ContainsKey(id - id % 10))
            {
                return GetTalk(id - id % 100, talkIndex);
            }
            else
            {
                return GetTalk(id - id % 10, talkIndex);
            } 
        }
            
        if (talkIndex == talkData[id].Length)
        {
            return null;
        }
        else
            return talkData[id][talkIndex];
    }

    public Sprite GetSprite(int id)
    {
        return portraitDate[id];
    }
}
