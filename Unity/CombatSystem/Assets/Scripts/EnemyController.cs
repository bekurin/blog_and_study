using System.Collections;
using UnityEngine;
using UnityEngine.UI;

public class EnemyController : CharacterStats
{
    public Transform attackTransform;
    public Image img;

    Vector2 prevPosition;
    bool isAlive;

    Animator animator;
    Rigidbody2D rigid;
    SpriteRenderer sprite;
    IEnumerator enemyMove;


    void Start()
    {
        isAlive = true;

        animator = GetComponent<Animator>();
        rigid = GetComponent<Rigidbody2D>();
        sprite = GetComponent<SpriteRenderer>();

        enemyMove = EnemyControl();
        StartCoroutine(enemyMove);
    }

    public override void TakeDamage(int damage)
    {
        base.TakeDamage(damage);
        animator.SetTrigger("onHit");
        img.fillAmount = health / maxHealth;
    }

    public override void Die()
    {
        animator.SetBool("isDeath", true);
        
        if(enemyMove != null)
        {
            StopCoroutine(enemyMove);
        }

        GetComponent<Collider2D>().enabled = false;
        this.enabled = false;

        GameManager.instance.GameOver(true);
    }

    Vector2 CheckTarget(Vector2 target)
    {
        Vector2 temp = target;

        if (target.x < GameManager.instance.limitPoint1.x)
        {
            temp = new Vector2(target.x + 2, target.y);
        }
        else if (target.x > GameManager.instance.limitPoint2.x)
        {
            temp = new Vector2(target.x - 2, target.y);
        }

        if (target.y > GameManager.instance.limitPoint1.y)
        {
            temp = new Vector2(target.x, target.y - 1);
        }
        else if (target.y < GameManager.instance.limitPoint2.y)
        {
            temp = new Vector2(target.x, target.y + 1);
        }

        return temp;
    }

    void Attack()
    {
        Collider2D[] players = Physics2D.OverlapCircleAll(attackTransform.position, attackRadius, LayerMask.GetMask("Player"));

        if (players != null)
        {
            animator.SetTrigger("onAttack");

            foreach (Collider2D player in players)
            {
                player.GetComponent<PlayerController>().TakeDamage(attackPower);
            }
        }
    }

    IEnumerator EnemyControl()
    {
        while (isAlive)
        {
            float x = transform.position.x + Random.Range(-1f, 1f);
            float y = transform.position.y + Random.Range(-1f, 1f);

            Vector2 target = new Vector2(x, y);
            target = CheckTarget(target);
            prevPosition = transform.position;

            if(!(target.x < prevPosition.x))
            {
                Vector3 scale = transform.localScale;
                scale.x = -Mathf.Abs(scale.x);
                transform.localScale = scale;
            }
            else
            {
                Vector3 scale = transform.localScale;
                scale.x = Mathf.Abs(scale.x);
                transform.localScale = scale;
            }

            if (Time.time > nextAttackTime)
            {
                Attack();
                nextAttackTime = Time.time + attackReload;

                yield return new WaitForSeconds(attackReload);
            }

            while (Vector2.Distance(transform.position, target) > 0.001f)
            {
                animator.SetBool("isMove", true);
                transform.position = Vector2.MoveTowards(transform.position, target, moveSpeed);
                yield return null;
            }
            animator.SetBool("isMove", false);

            isAlive = GameManager.instance.isOver;
            float randomIdleTime = Random.Range(0.5f, 1f);
            yield return new WaitForSeconds(randomIdleTime);
        }
    }

    void OnDrawGizmosSelected()
    {
        Gizmos.color = Color.yellow;
        Gizmos.DrawWireSphere(attackTransform.position, attackRadius);
    }
}
