using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class GameManager : MonoBehaviour
{
    public static GameManager GM;
    public static long money;

    public GameObject prefabCoffee;
    public GameObject prefabEmployee;
    public GameObject prefabTextMoney;

    public Sprite[] spriteF;
    public Sprite[] spriteM;

    public Vector2 limitPoint1;
    public Vector2 limitPoint2;

    public Text textMoney;

    public static string familyname
    {
        get
        {
            string[] names = new string[10];

            names[0] = "김";
            names[1] = "이";
            names[2] = "박";
            names[3] = "전";
            names[4] = "홍";
            names[5] = "남궁";
            names[6] = "심";
            names[7] = "임";
            names[8] = "조";
            names[9] = "석";

            int random = Random.Range(0, names.Length);
            string name = names[random];
            return name;
        }
    }
    public static string name
    {
        get
        {
            string[] names = new string[10];

            names[0] = "하나";
            names[1] = "둘";
            names[2] = "셋";
            names[3] = "넷";
            names[4] = "다섯";
            names[5] = "여섯";
            names[6] = "일곱";
            names[7] = "여덟";
            names[8] = "아홉";
            names[9] = "열";

            int random = Random.Range(0, names.Length);
            string name = names[random];
            return name;
        }
    }

    public Sprite[] spriteButtonState;
    public Image imgButton;
    public bool isWhip;

    public GameObject panelInfo;

    public List<Employee> emps;

    private string savePath;

    void Awake()
    {
        GM = this;
        savePath = Application.persistentDataPath + "/save.sav";
    }

    void Start()
    {
        emps = new List<Employee>();
        money = 100000;
        if (System.IO.File.Exists(savePath))
        {
            Load();
            foreach(var employee in emps)
            {
                InitializeEmployee(employee);
            }
        }
    }

    void Update()
    {
        if (EventSystem.current.IsPointerOverGameObject() == false)
        {
            if (isWhip == true)
                EarnMoney();
            else
                CreateCoffee();
        }

        if(GameObject.Find("PanelOption") != null)
        {
            Time.timeScale = 0;
        }
        else
        {
            Time.timeScale = 1;
        }

        ShowMoneyText();
        ChangeButtonSprite();
    }

    void CreateCoffee()
    {
        if (Input.GetMouseButtonDown(0))
        {
            Vector2 mousePosition = Camera.main.ScreenToWorldPoint(Input.mousePosition);
            Instantiate(prefabCoffee, mousePosition, Quaternion.identity);
        }
    }

    void OnDrawGizmos()
    {
        Vector2 limitPoint3 = new Vector2(limitPoint2.x, limitPoint1.y);
        Vector2 limitPoint4 = new Vector2(limitPoint1.x, limitPoint2.y);

        Gizmos.color = Color.red;

        Gizmos.DrawLine(limitPoint1, limitPoint3);
        Gizmos.DrawLine(limitPoint3, limitPoint2);
        Gizmos.DrawLine(limitPoint1, limitPoint4);
        Gizmos.DrawLine(limitPoint4, limitPoint2);
    }

    void ShowMoneyText()
    {
        textMoney.text = money.ToString("###,###");
    }

    void ChangeButtonSprite()
    {
        if (isWhip == true)
        {
            imgButton.sprite = spriteButtonState[0];
            imgButton.rectTransform.sizeDelta = new Vector2(250, 250);
        }
        else
        {
            imgButton.sprite = spriteButtonState[1];
            imgButton.rectTransform.sizeDelta = new Vector2(180, 250);
        }
    }

    void CreateEmployee(Employee employee)
    {
        GameObject obj = Instantiate(prefabEmployee, Vector3.zero, Quaternion.identity);
        obj.GetComponent<EmployeeControl>().info = employee;
    }


    void InitializeEmployee(Employee employee)
    {
        GameObject obj = Instantiate(prefabEmployee, Vector3.zero, Quaternion.identity);
        obj.GetComponent<EmployeeControl>().info = employee;
    }

    public void Hire(int price, Employee employee)
    {
        if (money >= price)
        {
            CreateEmployee(employee);
            money -= price;
            emps.Add(employee);
        }
    }

    public void ChangeButtonState()
    {
        if (isWhip == true)
        {
            isWhip = false;
        }
        else
        {
            isWhip = true;
        }
    }

    public void EarnMoney()
    {
        if (Input.GetMouseButtonDown(0))
        {
            money += 1;
        }
    }

    public void PanelHireOpen()
    {
        panelInfo.SetActive(true);

        Employee employee = RandomCreateEmployee();

        var textName = panelInfo.transform.Find("TextName").GetComponent<Text>();
        var imageCharacter = panelInfo.transform.Find("ImageCharacter").GetComponent<Image>();
        var textProgramming = panelInfo.transform.Find("PanelAbility/ImageProgramming/TextProgramming").GetComponent<Text>();
        var textDesign = panelInfo.transform.Find("PanelAbility/ImageDesign/TextDesign").GetComponent<Text>();
        var textSound = panelInfo.transform.Find("PanelAbility/ImageSound/TextSound").GetComponent<Text>();
        var textArt = panelInfo.transform.Find("PanelAbility/ImageArt/TextArt").GetComponent<Text>();
        var textSalery = panelInfo.transform.Find("ButtonHire/Image/TextMoney").GetComponent<Text>();
        var buttonHire = panelInfo.transform.Find("ButtonHire").GetComponent<Button>();

        textName.text = employee.name;

        if (employee.gender == Gender.Female)
        {
            imageCharacter.sprite = spriteF[1];
        }
        else
        {
            imageCharacter.sprite = spriteM[1];
        }

        textProgramming.text = employee.programming.ToString();
        textDesign.text = employee.design.ToString();
        textSound.text = employee.sound.ToString();
        textArt.text = employee.art.ToString();
        textSalery.text = employee.salery.ToString();

        buttonHire.onClick.RemoveAllListeners();
        buttonHire.onClick.AddListener(delegate
        {
            Hire((int)employee.salery, employee);
        });
    }

    public void Save()
    {
        SaveDate sd = new SaveDate();
        sd.money = money;
        sd.empList = emps;
        XmlManager.XmlSave<SaveDate>(sd, savePath);
    }

    public void Load()
    {
        SaveDate sd = XmlManager.XmlLoad<SaveDate>(savePath);
        money = sd.money;
        emps = sd.empList;
    }

    public void SaveDelete()
    {
        if (System.IO.File.Exists(savePath))
        {
            System.IO.File.Delete(savePath);
        }
    }

    void OnApplicationQuit()
    {
        Save();
        Application.Quit();
    }

    Employee RandomCreateEmployee()
    {
        Employee info = new Employee();

        info.name = familyname + name;
        info.hp = 100;

        info.design = Random.Range(0, 101);
        info.programming = Random.Range(0, 101);
        info.sound = Random.Range(0, 101);
        info.art = Random.Range(0, 101);

        info.salery = Random.Range(100, 1001);

        int randomGender = Random.Range(0, 2);
        info.gender = (Gender)randomGender;

        return info;
    }

    public void IapTest()
    {
        money += 10000;
    }
}

[System.Serializable]
public class SaveDate
{
    public long money;

    public List<Employee> empList;
}
