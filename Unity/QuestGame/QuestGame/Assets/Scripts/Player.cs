using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player : MonoBehaviour
{
    public float speed;
    public GameManager manager;
    public GameObject background;

    Animator anim;
    Rigidbody2D rigid;
    Vector3 playerDir;
    BackgroundScrolling[] back;
    GameObject scanObj;

    bool isHorizontalMove;
    float hInput;
    float vInput;

    void Start()
    {
        rigid = GetComponent<Rigidbody2D>();
        anim = GetComponent<Animator>();
        back = background.GetComponentsInChildren<BackgroundScrolling>();
    }

    void Update()
    {
        OnMove();

        if (Input.GetButtonDown("Jump") && scanObj != null)
        {
            manager.ShowText(scanObj);
        }
    }

    void FixedUpdate()
    {
        Vector2 moveVec = isHorizontalMove ? new Vector2(hInput, 0) : new Vector2(0, vInput);
        rigid.velocity = moveVec * speed;

        Debug.DrawRay(rigid.position, playerDir * 1.0f, Color.red);
        RaycastHit2D rayHit = Physics2D.Raycast(rigid.position, playerDir, 1.0f, LayerMask.GetMask("Object"));

        if (rayHit.collider != null)
        {
            scanObj = rayHit.collider.gameObject;
        }
        else
        {
            scanObj = null;
        }
    }

    void OnMove()
    {
        if(manager.isMove)
            return;

        hInput = Input.GetAxisRaw("Horizontal");
        vInput = Input.GetAxisRaw("Vertical");

        if (hInput == 0 && vInput != 0)
        {
            isHorizontalMove = false;

            if (vInput == 1)
                playerDir = Vector3.up;
            else if (vInput == -1)
                playerDir = Vector3.down;
        }
        else
        {
            isHorizontalMove = true;
            
            if (hInput == 1)
                playerDir = Vector3.right;
            else if (hInput == -1)
                playerDir = Vector3.left;
        }

        int idleInput = (int)Mathf.Abs(hInput) + (int)Mathf.Abs(vInput);

        if (hInput == 1)
        {
            for (int i = 0; i < back.Length; i++)
            {
                back[i].OnRightMove();
            }
        }
        else if (hInput == -1)
        {
            for (int i = 0; i < back.Length; i++)
            {
                back[i].OnLeftMove();
            }
        }

        anim.SetInteger("doWalk", idleInput);
    }
}
