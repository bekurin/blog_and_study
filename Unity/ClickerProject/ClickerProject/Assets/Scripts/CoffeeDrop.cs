using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CoffeeDrop : MonoBehaviour
{
    public float floorHeight;
    public float groundHeight;

    void Start()
    {
        StartCoroutine(Drop());
    }

    void Update()
    {
        
    }

    IEnumerator Drop()
    {
        float t = 0.3f;

        while (t > 0 || transform.position.y > floorHeight)
        {
            transform.Translate(Vector2.down * 0.03f);

            t -= Time.deltaTime;

            yield return null;
        }

        yield return null;
    }
}
