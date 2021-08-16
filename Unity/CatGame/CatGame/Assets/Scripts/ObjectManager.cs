using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObjectManager : MonoBehaviour
{
    public GameObject car1Prefab;
    public GameObject car2Prefab;
    public GameObject car3Prefab;
    public GameObject car4Prefab;
    public GameObject car5Prefab;
    public GameObject car6Prefab;
    public GameObject car7Prefab;
    public GameObject car8Prefab;
    public GameObject car9Prefab;

    GameObject[] car1;
    GameObject[] car2;
    GameObject[] car3;
    GameObject[] car4;
    GameObject[] car5;
    GameObject[] car6;
    GameObject[] car7;
    GameObject[] car8;
    GameObject[] car9;

    GameObject[] targetPool;

    void Start()
    {
        car1 = new GameObject[3];
        car2 = new GameObject[3];
        car3 = new GameObject[3];
        car4 = new GameObject[3];
        car5 = new GameObject[3];
        car6 = new GameObject[3];
        car7 = new GameObject[3];
        car8 = new GameObject[3];
        car9 = new GameObject[3];

        Generate();
    }

    void Generate()
    {
        for (int i = 0; i < car1.Length; i++)
        {
            car1[i] = Instantiate(car1Prefab);
            car1[i].SetActive(false);
        }

        for (int i = 0; i < car2.Length; i++)
        {
            car2[i] = Instantiate(car2Prefab);
            car2[i].SetActive(false);
        }

        for (int i = 0; i < car3.Length; i++)
        {
            car3[i] = Instantiate(car3Prefab);
            car3[i].SetActive(false);
        }

        for (int i = 0; i < car4.Length; i++)
        {
            car4[i] = Instantiate(car4Prefab);
            car4[i].SetActive(false);
        }

        for (int i = 0; i < car5.Length; i++)
        {
            car5[i] = Instantiate(car5Prefab);
            car5[i].SetActive(false);
        }

        for (int i = 0; i < car6.Length; i++)
        {
            car6[i] = Instantiate(car6Prefab);
            car6[i].SetActive(false);
        }

        for (int i = 0; i < car7.Length; i++)
        {
            car7[i] = Instantiate(car7Prefab);
            car7[i].SetActive(false);
        }

        for (int i = 0; i < car8.Length; i++)
        {
            car8[i] = Instantiate(car8Prefab);
            car8[i].SetActive(false);
        }

        for (int i = 0; i < car9.Length; i++)
        {
            car9[i] = Instantiate(car9Prefab);
            car9[i].SetActive(false);
        }
    }

    public GameObject MakeObject(string type)
    {
        switch (type)
        {
            case "car1":
                targetPool = car1;
                break;
            case "car2":
                targetPool = car2;
                break;
            case "car3":
                targetPool = car3;
                break;
            case "car4":
                targetPool = car4;
                break;
            case "car5":
                targetPool = car5;
                break;
            case "car6":
                targetPool = car6;
                break;
            case "car7":
                targetPool = car7;
                break;
            case "car8":
                targetPool = car8;
                break;
            case "car9":
                targetPool = car9;
                break;
        }

        for (int i = 0; i < targetPool.Length; i++)
        {
            if (!targetPool[i].activeSelf)
            {
                targetPool[i].SetActive(true);
                return targetPool[i];
            }
        }
        return null;
    }
}
