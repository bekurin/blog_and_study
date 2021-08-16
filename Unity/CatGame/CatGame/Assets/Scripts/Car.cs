using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Car : MonoBehaviour
{
    public float moveSpeed;
    public Vector2 initTarget = new Vector2(20, 0);

    Rigidbody2D rigid;
    Vector2 target;

    void Start()
    {
        rigid = GetComponent<Rigidbody2D>();
    }

    void OnEnable()
    {
        StartCoroutine(Move());    
    }

    void OnDisable()
    {
        transform.position = initTarget;
        StopCoroutine(Move());
    }

    IEnumerator Move()
    {
        float y = transform.position.y + Random.Range(-3, 4);

        while (true)
        {
            float x = transform.position.x - 1f;

            target = new Vector2(x, y);

            while (Vector2.Distance(transform.position, target) > 0.01f)
            {
                transform.position = Vector2.MoveTowards(transform.position, target, moveSpeed);
                yield return null;
            }

            yield return new WaitForSeconds(0.1f);
        }
    }
}
