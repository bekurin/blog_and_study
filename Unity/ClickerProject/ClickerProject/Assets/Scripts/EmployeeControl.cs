using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class EmployeeControl : MonoBehaviour
{
    SpriteRenderer sprite;

    public Employee info;

    public float speed;

    public Vector2 prevPosition;

    void Start()
    {
        sprite = GetComponent<SpriteRenderer>();

        if (string.IsNullOrEmpty(info.name))
        {
            SetInfo();
        }

        StartCoroutine(EarmMoneyAuto());
        StartCoroutine(Move());
        StartCoroutine(HpDecreaseAuto());
    }

    void Update()
    {
        SpriteChange();
        ShowInfo();
    }

    IEnumerator EarmMoneyAuto()
    {
        while (true)
        {
            GameManager.money += 10;
            ShowTextMoney(10);
            yield return new WaitForSeconds(1.0f);
        }
    }

    void ShowTextMoney(int money)
    {
        GameObject obj = Instantiate(GameManager.GM.prefabTextMoney, transform.Find("Canvas"), false);

        Animator anim = obj.GetComponent<Animator>();
        anim.SetTrigger("Start");

        Text txt = obj.GetComponent<Text>();
        txt.text = "+ " + money.ToString("###,###");

        Destroy(obj, 3f);
    }

    IEnumerator HpDecreaseAuto()
    {
        while (true)
        {
            info.hp -= 1;
            if(info.hp <= 0)
            {
                Destroy(gameObject);
            }
            yield return new WaitForSeconds(1.0f);
        }
    }

    IEnumerator Move()
    {
        while (true)
        {
            float x = transform.position.x + Random.Range(-2f, 2f);
            float y = transform.position.y + Random.Range(-2f, 2f);

            Vector2 target = new Vector2(x, y);
            target = CheckTarget(target);

            prevPosition = transform.position;

            while (Vector2.Distance(transform.position, target) > 0.01f)
            {
                transform.position = Vector2.MoveTowards(transform.position, target, speed);
                yield return null;
            }
            yield return new WaitForSeconds(0.1f);
        }
    }

    Vector2 CheckTarget(Vector2 curTarget)
    {
        Vector2 temp = curTarget;

        if (curTarget.x < GameManager.GM.limitPoint1.x)
        {
            temp = new Vector2(curTarget.x + 4, temp.y);
        }
        else if (curTarget.x > GameManager.GM.limitPoint2.x)
        {
            temp = new Vector2(curTarget.x - 4, temp.y);
        }

        if (curTarget.y > GameManager.GM.limitPoint1.y)
        {
            temp = new Vector2(temp.x, curTarget.y - 4);
        }
        else if (curTarget.y < GameManager.GM.limitPoint2.y)
        {
            temp = new Vector2(temp.x, curTarget.y + 4);
        }

        return temp;
    }

    void SpriteChange()
    {
        Sprite[] set = null;

        if (info.gender == Gender.Female)
        {
            set = GameManager.GM.spriteF;
        }
        else
        {
            set = GameManager.GM.spriteM;
        }

        Vector2 abs = (Vector2)transform.position - prevPosition;

        if (Mathf.Abs(abs.x) > Mathf.Abs(abs.y))
        {
            sprite.sprite = set[2];
            if (transform.position.x > prevPosition.x)
            {
                sprite.flipX = false;
            }
            else if (transform.position.x < prevPosition.x)
            {
                sprite.flipX = true;
            }
        }
        else
        {
            sprite.flipX = false;
            if (transform.position.y > prevPosition.y)
            {
                sprite.sprite = set[0];
            }
            else if (transform.position.y < prevPosition.y)
            {
                sprite.sprite = set[1];
            }
        }
    }

    void SetInfo()
    {
        info.name = GameManager.familyname + GameManager.name;
        info.hp = 100;

        info.design = Random.Range(0, 101);
        info.programming = Random.Range(0, 101);
        info.sound = Random.Range(0, 101);
        info.art = Random.Range(0, 101);

        info.salery = Random.Range(100, 1001);

        int randomGender = Random.Range(0, 2);
        info.gender = (Gender)randomGender;
    }

    void ShowInfo()
    {
        Text txt = transform.Find("Canvas/TextName").GetComponent<Text>();
        txt.text = info.name;

        Image img = transform.Find("Canvas/Image/ImageGauge").GetComponent<Image>();
        img.fillAmount = info.hp / 100f;
    }

    void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.tag == "Coffee")
        {
            Debug.Log("커피를 마셨다!");
            info.hp = 100;
            Destroy(collision.gameObject);
        }
    }

    /*
    void OnCollisionEnter2D(Collision2D collision)
    {
        if(collision.gameObject.tag == "Employee")
        {
            Collider2D col1 = GetComponent<Collider2D>();
            Collider2D col2 = collision.collider;
            Physics2D.IgnoreCollision(col1, col2);
        }
    }
    */
}

public enum Gender
{
    Female = 0,
    Male = 1
}

[System.Serializable]
public class Employee
{
    public string name;
    public Gender gender;

    public float design;
    public float programming;
    public float art;
    public float sound;

    public float hp;

    public long salery;
}
