using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player : MonoBehaviour
{
    public int maxSpeed;
    public int jumpPower;

    Rigidbody2D rigid;
    Animator anim;
    SpriteRenderer sprite;
    Vector3 playerPos;

    void Start()
    {
        rigid = GetComponent<Rigidbody2D>();
        anim = GetComponent<Animator>();
        sprite = GetComponent<SpriteRenderer>();

        playerPos = transform.position;
    }

    void Update()
    {
        Move();
        Walk();
        Jump();
    }

    void FixedUpdate()
    {
        LandingJump();
        MaxSpeed();
    }

    void Walk()
    {
        if (rigid.velocity.x == 0)
        {
            anim.SetBool("isRun", false);
        }
        else
        {
            anim.SetBool("isRun", true);
        }
    }

    void Move()
    {
        float h = Input.GetAxisRaw("Horizontal");

        rigid.AddForce(Vector2.right * h, ForceMode2D.Impulse);
    }

    void MaxSpeed()
    {
        if (rigid.velocity.x > maxSpeed)
        {
            rigid.velocity = new Vector2(maxSpeed, rigid.velocity.y);
        }
        else if (rigid.velocity.x < maxSpeed * (-1))
        {
            rigid.velocity = new Vector2(maxSpeed * (-1), rigid.velocity.y);
        }
    }

    void Jump()
    {
        if (Input.GetButtonDown("Jump") && !anim.GetBool("isJump"))
        {
            rigid.AddForce(Vector2.up * jumpPower, ForceMode2D.Impulse);
            anim.SetBool("isJump", true);
        }
    }

    void LandingJump()
    {
        if (rigid.velocity.y < 0)
        {
            RaycastHit2D raycastHit = Physics2D.Raycast(rigid.position, Vector2.down, 1, LayerMask.GetMask("Car"));

            if (raycastHit.collider != null)
            {
                if (raycastHit.distance <= 1f)
                {
                    anim.SetBool("isJump", false);
                }
            }
        }
    }

    void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.collider.tag == "Border")
        {
            OnHit();
        }
    }

    void OnHit()
    {
        GameManager.GM.HelathDown();
        gameObject.layer = 10;
        gameObject.SetActive(false);

        if (GameManager.GM.health > 0)
        {
            Invoke("OnRespawn", 1f);
        }
    }

    void OnRespawn()
    {
        sprite.color = new Color(1, 1, 1, 0.5f);
        transform.position = playerPos;
        transform.rotation = Quaternion.identity;
        gameObject.SetActive(true);
        Invoke("CanDamaged", 1.5f);
    }

    void CanDamaged()
    {
        gameObject.layer = 8;
        sprite.color = new Color(1, 1, 1, 1);
    }
}
