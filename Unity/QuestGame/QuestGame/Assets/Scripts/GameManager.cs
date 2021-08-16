using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public Text talkText;
    public Text questText;
    public QuestManager questManager;
    public TalkManager talkManager;
    public GameObject TalkImage;
    public GameObject scanObject;
    public Image portraitImage;

    public bool isMove;
    public int talkIndex;

    void Start()
    {
        questText.text = questManager.CheckQuest();
        Debug.Log(questManager.CheckQuest());
    }

    public void ShowText(GameObject scanObj)
    {
        scanObject = scanObj;
        ObjectData objectData = scanObject.GetComponent<ObjectData>();
        OnTalk(objectData.id, objectData.isNpc);

        TalkImage.SetActive(isMove);
    }

    void OnTalk(int id, bool isNpc)
    {
        int questTalkIndex = questManager.GetQuestTalkIndex(id);
        string talkData = talkManager.GetTalk(id + questTalkIndex, talkIndex);

        if(talkData == null)
        {
            isMove = false;
            talkIndex = 0;
            questText.text = questManager.CheckQuest(id);
            Debug.Log(questManager.CheckQuest(id));
            return;
        }

        if (isNpc)
        {
            talkText.text = talkData;
            portraitImage.sprite = talkManager.GetSprite(id);
            portraitImage.color = new Color(1, 1, 1, 1);
        }
        else
        {
            talkText.text = talkData;
            portraitImage.color = new Color(1, 1, 1, 0);
        }

        isMove = true;
        talkIndex++;
    }
}
