using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObjectBorder : MonoBehaviour
{
    void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.tag == "Car")
        {
            GameManager.GM.AddScore(100);
            collision.gameObject.SetActive(false);
        }    
    }
}
